package com.billme.ui;

import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

public class RelationActivity extends FragmentActivity {
		private TabHost tabHost = null;
		private FriendFragment friendFragment = null;
		private EnterpriseFragment enterpriseFragment = null;
		private int currentTab = 0;
		private FragmentTransaction ft = null;
		private RelativeLayout tabIndicator1, tabIndicator2;

		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_relation);
			initTabHost();
			
		}

//		protected void onActivityResult(int requestCode, int resultCode, Intent data)
//		{
//			switch (resultCode / 10)
//			{
//			case 1:
//			{
//				FragmentManager fm = getSupportFragmentManager();
//				enterpriseFragment = (enterpriseFragment) fm.findFragmentByTag("pog");
//				enterpriseFragment.modifyData(requestCode, resultCode % 10, data);
//				break;
//			}
//
//			}
//		}

		private void initTabHost()
		{
			tabHost = (TabHost) findViewById(android.R.id.tabhost);
			findTabView();
			tabHost.setup();
			tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
			{
				@Override
				public void onTabChanged(String tabId)
				{
					// TODO Auto-generated method stub
					FragmentManager fm = getSupportFragmentManager();
					friendFragment = (FriendFragment) fm.findFragmentByTag("friend");
					enterpriseFragment = (EnterpriseFragment) fm
							.findFragmentByTag("enterprise");
					ft = fm.beginTransaction();
					if (friendFragment != null)
					{
						ft.detach(friendFragment);
					}
					if (enterpriseFragment != null)
					{
						ft.detach(enterpriseFragment);
					}
					if (tabId.equalsIgnoreCase("friend"))
					{
						if (friendFragment == null)
							ft.add(android.R.id.tabcontent, new FriendFragment(),
									"friend");
						else
							ft.attach(friendFragment);
						currentTab = 0;
					}
					else if (tabId.equalsIgnoreCase("enterprise"))
					{
						if (enterpriseFragment == null)
							ft.add(android.R.id.tabcontent, new EnterpriseFragment(),
									"enterprise");
						else
							ft.attach(enterpriseFragment);
						currentTab = 1;
					}
					else
						switch (currentTab)
						{
						case 0:
							ft.attach(friendFragment);
							break;
						case 1:
							ft.attach(enterpriseFragment);
							break;
						default:
							ft.attach(friendFragment);
							break;
						}
					ft.commit();
				}
			});
			initTab();
			tabHost.setCurrentTab(0);
		}
		
		private void findTabView()
		{
			LinearLayout layout = (LinearLayout) tabHost.getChildAt(0);
			TabWidget tw = (TabWidget) layout.getChildAt(1);

			tabIndicator1 = (RelativeLayout) LayoutInflater.from(this).inflate(
					R.layout.my_tab, tw, false);
			TextView tvTab1 = (TextView) tabIndicator1.getChildAt(0);
			tvTab1.setText("好友");

			tabIndicator2 = (RelativeLayout) LayoutInflater.from(this).inflate(
					R.layout.my_tab, tw, false);
			TextView tvTab2 = (TextView) tabIndicator2.getChildAt(0);
			tvTab2.setText("关注商家");

		}

		private void initTab()
		{
			TabHost.TabSpec tSpecDail = tabHost.newTabSpec("friend");
			tSpecDail.setIndicator(tabIndicator1).setContent(
					new DummyTabContent(this));
			tabHost.addTab(tSpecDail);

			TabHost.TabSpec tSpecPog = tabHost.newTabSpec("enterprise");
			tSpecPog.setIndicator(tabIndicator2).setContent(
					new DummyTabContent(this));
			tabHost.addTab(tSpecPog);

		}
	
	
}
class DummyTabContent implements TabContentFactory
{
	private Context mContext;
	public DummyTabContent(Context context)
	{
		mContext = context;
	}

	@Override
	public View createTabContent(String tag)
	{
		View v = new View(mContext);
		return v;
	}
}