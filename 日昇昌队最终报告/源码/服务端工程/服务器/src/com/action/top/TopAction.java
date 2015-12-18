package com.action.top;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.util.JsonData;
import com.util.OnlineListService;
import com.util.ServiceType;

@SuppressWarnings("serial")
public class TopAction extends ActionSupport implements SessionAware,ApplicationAware{
	
	private Map<String, Object> application;
	private Map<String, Object> session;
	private String json;	
	
	
	public String execute(){
		System.out.println(json);
		JsonData jsonData = (JsonData) JSONObject.toBean(JSONObject.fromObject(json), JsonData.class);
		if(jsonData==null)
			System.out.println("empty");
		//�ӽ��ܵ��������ó�name,data,serviceType
		String name = jsonData.getName();
		System.out.println(name+" �û���¼��");
		JSONObject data = jsonData.getData();
		System.out.println(data);
		int serviceType = jsonData.getServiceType();
		System.out.println("�������� = "+serviceType);
		//data����session��
		session.put("data", data);
		//name����session��
		session.put("name", name);
		//��application����������������û���򴴽�һ���Ž�ȥ
		if(!application.containsKey("onlineList")){
			OnlineListService.create(application);
			System.out.println("������������");
		}
		if(serviceType==ServiceType.LOGIN){
			//��Ϊ��¼��������������¼ҳ��
			System.out.println("ת����¼ҳ��");
			return "login";
		}
		if(serviceType==ServiceType.REGIST){
			//��Ϊע��������������ע��ҳ��
			System.out.println("ת��ע��ҳ��");
			return "regist";
		}
		if(serviceType==ServiceType.CHECK_USER_EXISTENCE){
			//��Ϊ��ѯ�û��Ƿ������������������
			System.out.println("��ѯ�û��Ƿ����");
			return "check_customer_exists";
		}
//		if(!OnlineListService.search(application,name)){
//			//�������������������У���������¼ҳ��
//			System.out.println("ת����¼ҳ��");
//			return "login";
//		}
		//�������񣿣���
		//TODO
		switch(serviceType){
//		case -201: return "logout";//ע��
//		case -216: return "get_user_info4customer";//��ȡ�û���Ϣ
		case -238: return "query_pay_traderecord";//����֧���Ľ��׼�¼
//		case -232: return "query_receive_traderecord";//�����տ�Ľ��׼�¼
//		case -217: return "modify_user_info4customer";//�����û���Ϣ
//		case -208: return "query_vipCard";//��ѯVIPCARD
//		case -209: return "apply_for_vipCard";//����VIPCARD
//		case -210: return "use_vipCard";//�û�Ա��֧��
//		case -211: return "query_user_grade";//��ѯ�û�ϵͳ����
		case -230: return "query_bankCard";//��ѯ���п�
		case -206: return "add_bankCard";//������п�
//		case -207: return "delete_bankCard";//ɾ�����п�
		case -204: return "personal_pay";//����֧��
		case -205: return "multiple_pay";//����֧��
		case -219: return "add_friendRequest";//��Ӻ�������
//		case -220: return "delete_friend";//ɾ������
//		case -221: return "focus_seller";//��ע�̼�
//		case -222: return "cancel_focus_seller";//ȡ����ע�̼�
		case -223: return "share_experience";//������������
		case -213: return "query_coupon";//��ѯ�Ż�ȯ
//		case -214: return "send_coupon";//�����Ż�ȯ
//		case -233: return "add_comment";//�������
		case -224: return "get_share";//��ȡ���ѷ���  
		case -229: return "get_surrounding_enterprise";//��ȡ�ܱ��̼�
//		case -234: return "get_comment4seller";//�̼һ�ȡ����
//		case -235: return "get_share";//��ȡ���˷���
//		case -236: return "set_VIP";//�̼Ҵ���VIP��
		case -231: return "get_friend";//��ȡ�����б�
		case -233: return "search_customer";//��ѯ�û�
		case -218: return "query_bill";
//		TODO	
		
		}
		System.out.println("error");
		return ERROR;
	}


	public Map<String, Object> getApplication() {
		return application;
	}


	public String getJson() {
		return json;
	}


	public Map<String, Object> getSession() {
		return session;
	}


	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}


	public void setJson(String json) {
		this.json = json;
	}


	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
