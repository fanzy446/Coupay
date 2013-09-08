package com.billme.ui;

import java.util.HashMap;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.futurePayment.constant.Task;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NFCPaymentActivity extends BaseActivity implements BillMeActivity,
		CreateNdefMessageCallback, OnNdefPushCompleteCallback {

	private NfcAdapter mNfcAdapter;
	private TextView mInfoText;
	private EditText money;
	private static final int MESSAGE_SENT = -100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfcpayment);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		mInfoText = (TextView) findViewById(R.id.tv_nfcpayment_text);
		money = (EditText) findViewById(R.id.et_nfcpayment_money);

		if (mNfcAdapter == null) {
			mInfoText.setText("NFC is not available on this device.");
		} else {
			// Register callback to set NDEF message
			mNfcAdapter.setNdefPushMessageCallback(this, this);
			// Register callback to listen for message-sent success
			mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if (mNfcAdapter == null) {
			return super.onCreateOptionsMenu(menu);
		}
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_nfcpayment, menu);
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

	/**
	 * Parses the NDEF Message from the intent and prints to the TextView
	 */
	void processIntent(Intent intent) {
		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		// record 0 contains the MIME type, record 1 is the AAR, if present
		if (mInfoText != null && msg != null) {
			String str = new String(msg.getRecords()[0].getPayload());
			mInfoText.setText(new String(msg.getRecords()[0].getPayload()));
			
			String[] temp = str.split("~");
			
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("receiver", temp[0]);
			param.put("money", temp[1]);
			param.put("method", "NFC");

			Task task = new Task(Task.TASK_NFC_PAY, param);
			MainService.newTask(task);
		} else
			Log.i("error", "nfc something is null");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		// onResume gets called after this to handle the intent
		setIntent(intent);
	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
		// A handler is needed to send messages to the activity when this
		// callback occurs, because it happens from a binder thread
		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
	}

	/** This handler receives a message from onNdefPushComplete */
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_SENT:
				Toast.makeText(getApplicationContext(), "Message sent!",
						Toast.LENGTH_LONG).show();
				break;
			}
		}
	};

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		Time time = new Time();
		time.setToNow();

		
		String payInfo = (MainService.getUser().getName() + "~" + money
				.getText().toString());
		String text = ("Beam me up!\n\n" + money.getText().toString()
				+ "Beam Time: " + time.format("%m/%d %H:%M"));
		NdefMessage msg = new NdefMessage(NdefRecord.createMime(
				"application/com.billme.ui", payInfo.getBytes()),
				NdefRecord.createMime("application/com.billme.ui",
						text.getBytes())
		/**
		 * The Android Application Record (AAR) is commented out. When a device
		 * receives a push with an AAR in it, the application specified in the
		 * AAR is guaranteed to run. The AAR overrides the tag dispatch system.
		 * You can add it back in to guarantee that this activity starts when
		 * receiving a beamed message. For now, this code uses the tag dispatch
		 * system.
		 */
		// ,NdefRecord.createApplicationRecord("com.example.android.beam")
		);
		return msg;
	}
}
