/**
 * 
 */
package com.mmednet.robotGuide.bean;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Administrator
 * 
 */
public class IntelligenceGuideSymptom {
	
	/** 身体症状关键字 **/
	private static final String BODY_WORDS = "全身、浑身、皮肤、淋巴、没劲、疼、痛、烧、体温、汗、冷、吃、梦、睡、胃口、晕、迷糊、昏倒、虚、醒、食欲、饿、厌食、饱、抽、痒、黄";
	
	/** 头颈部症状关键字 **/
	private static final String HEAD_WORDS = "头、脑、额、脖子、颈、眼、鼻、耳、嗓子、咽、喉、牙、嘴、口、舌、脸、面、腮、扁桃体、肿、咳、咯、痰、晕、血、疼、痒、痛、紫、青、迷糊、转、昏";
	
	/** 胸部症状关键字 **/
	private static final String CHEST_WORDS = "胸、肋骨、后背、乳房、乳腺、肺、心、腔、气管、憋、闷、慌、跳、岔气、疼、痛、块";
	
	/** 腹部症状关键字 **/
	private static final String ABDOMEN_WORDS = "腹、胆、脾、腰、膀胱、阴道、输尿管、肚、脐、心口、肠、肝、阑尾、胃、肾、卵巢、子宫、输卵管、胰、恶心、呕吐、疼、尿、块、胀、泻、嗝、痛、顶、扎";
	
	/** 肢体症状关键字 **/
	private static final String LIMB_WORDS = "手、脚、腿、腰、臂、膝、髋、腕、指、趾、掌、肘、肩、腋、关节、抽、青、紫"; 

	Hashtable<String, ArrayList<Symptom>> symptom_h = new Hashtable<String, ArrayList<Symptom>>();
	
	String sort = "";
	
	private List<Integer> sortList = new ArrayList<Integer>();
	

	public void sortSymptom(String words) {
		UUID uuid = UUID.randomUUID();

		// 身体症状, 头颈部症状, 胸部症状, 腹部症状, 肢体症状
		String sort = "bodySymptom,headNeckSymptom,chestSymptom,abdomenSymptom,limbSymptom";
		
		if (!TextUtils.isEmpty(words)) {
			List<SymptomParam> list = new ArrayList<SymptomParam>();

			Map<String, List<String>> symptomMap = new HashMap<String, List<String>>();
			List<String> keywords = new ArrayList<String>();
			for (String word : BODY_WORDS.split("、")) {
				keywords.add(word);
			}
			symptomMap.put("bodySymptom", keywords);
			keywords = new ArrayList<String>();
			for (String word : HEAD_WORDS.split("、")) {
				keywords.add(word);
			}
			symptomMap.put("headNeckSymptom", keywords);
			keywords = new ArrayList<String>();
			for (String word : CHEST_WORDS.split("、")) {
				keywords.add(word);
			}
			symptomMap.put("chestSymptom", keywords);
			keywords = new ArrayList<String>();
			for (String word : ABDOMEN_WORDS.split("、")) {
				keywords.add(word);
			}
			symptomMap.put("abdomenSymptom", keywords);
			keywords = new ArrayList<String>();
			for (String word : LIMB_WORDS.split("、")) {
				keywords.add(word);
			}
			symptomMap.put("limbSymptom", keywords);
			
			for (String key : symptomMap.keySet()) {
				int counter = 0;
				for (String k : symptomMap.get(key)) {
					if (words.indexOf(k) != -1) {
						counter += 1;
					}
				}
				list.add(new SymptomParam(counter, key));
			}
			
			Collections.sort(list, new SymptomParam());
			Collections.reverse(list);
			
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				result.append(list.get(i).getName());
				if (i < list.size() - 1) {
					result.append(",");
				}
			}
			
			this.sort = result.toString();
		} else {
			this.sort = sort;
		}
	}
	
	public List<Integer> getSortList() {
		return sortList;
	}

	public void setSortList(List<Integer> sortList) {
		this.sortList = sortList;
	}

	public Hashtable<String, ArrayList<Symptom>> getSymptom_h() {
		return symptom_h;
	}

	public void setSymptom_h(Hashtable<String, ArrayList<Symptom>> symptom_h) {
		this.symptom_h = symptom_h;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public IntelligenceGuideSymptom() {
		// 全身症状
		ArrayList<Symptom> bodySymptom = new ArrayList<Symptom>();
		Symptom s1 = new Symptom();
		Symptom s2 = new Symptom();
		Symptom s3 = new Symptom();
		Symptom s4 = new Symptom();
		Symptom s5 = new Symptom();
		Symptom s6 = new Symptom();
		s1.setId(1);
		s1.setSymptomName("您有发热吗？");
		bodySymptom.add(s1);
		s2.setId(2);
		s2.setSymptomName("您最近有抽搐或短暂昏迷吗？");
		bodySymptom.add(s2);
		s3.setId(3);
		s3.setSymptomName("您感觉浑身没劲、出虚汗吗？");
		bodySymptom.add(s3);
		s4.setId(4);
		s4.setSymptomName("您最近有失眠吗？");
		bodySymptom.add(s4);
		s5.setId(5);
		s5.setSymptomName("您最近有没有食欲？");
		bodySymptom.add(s5);
		s6.setId(6);
		s6.setSymptomName("您最近皮肤或眼睛有明显发黄吗？");
		bodySymptom.add(s6);
		symptom_h.put("bodySymptom", bodySymptom);
		// 头颈症状
		ArrayList<Symptom> headNeckSymptom = new ArrayList<Symptom>();
		Symptom s7 = new Symptom();
		Symptom s8 = new Symptom();
		Symptom s9 = new Symptom();
		Symptom s10 = new Symptom();
		Symptom s11 = new Symptom();
		Symptom s12 = new Symptom();
		Symptom s13 = new Symptom();
		s7.setId(7);
		s7.setSymptomName("您有头部或面部肿胀吗？");
		headNeckSymptom.add(s7);
		s8.setId(8);
		s8.setSymptomName("您最近有咳嗽或咳痰吗？");
		headNeckSymptom.add(s8);
		s9.setId(9);
		s9.setSymptomName("您最近有咳血吗？");
		headNeckSymptom.add(s9);
		s10.setId(10);
		s10.setSymptomName("您最近有口唇或手指或脚趾发青发紫吗？");
		headNeckSymptom.add(s10);
		s11.setId(11);
		s11.setSymptomName("您有头疼吗？");
		headNeckSymptom.add(s11);
		s12.setId(12);
		s12.setSymptomName("您有头晕吗？");
		headNeckSymptom.add(s12);
		s13.setId(13);
		s13.setSymptomName("您最近有突然昏倒吗？");
		headNeckSymptom.add(s13);
		symptom_h.put("headNeckSymptom", headNeckSymptom);
		// 胸部症状
		ArrayList<Symptom> chestSymptom = new ArrayList<Symptom>();
		Symptom s14 = new Symptom();
		Symptom s15 = new Symptom();
		Symptom s16 = new Symptom();
		Symptom s17 = new Symptom();
		Symptom s18 = new Symptom();
		Symptom s19 = new Symptom();
		Symptom s20 = new Symptom();
		Symptom s21 = new Symptom();
		s14.setId(14);
		s14.setSymptomName("您有胸疼吗？");
		chestSymptom.add(s14);
		s15.setId(15);
		s15.setSymptomName("您最近感觉呼吸困难吗?");
		chestSymptom.add(s15);
		s16.setId(16);
		s16.setSymptomName("您最近感觉心慌吗？");
		chestSymptom.add(s16);
		s17.setId(17);
		s17.setSymptomName("您最近有感觉恶心或呕吐吗？");
		chestSymptom.add(s17);
		s18.setId(18);
		s18.setSymptomName("您最近有皮肤出血或紫点、紫斑吗？");
		chestSymptom.add(s18);
		s19.setId(19);
		s19.setSymptomName("您最近有感觉恶心或呕吐吗？");
		chestSymptom.add(s19);
		s20.setId(20);
		s20.setSymptomName("您有胸部硬结或肿块吗？");
		chestSymptom.add(s20);
		s21.setId(21);
		s21.setSymptomName("您最近有腰背疼吗？");
		chestSymptom.add(s21);
		symptom_h.put("chestSymptom", chestSymptom);
		//腹部症状
		ArrayList<Symptom> abdomenSymptom = new ArrayList<Symptom>();
		Symptom s22 = new Symptom();
		Symptom s23 = new Symptom();
		Symptom s24 = new Symptom();
		Symptom s25 = new Symptom();
		Symptom s26 = new Symptom();
		Symptom s33 = new Symptom();
		Symptom s34 = new Symptom();
		Symptom s35 = new Symptom();
		Symptom s36 = new Symptom();
		s22.setId(22);
		s22.setSymptomName("您最近有大便出血或呈黑褐色吗？");
		abdomenSymptom.add(s22);
		s23.setId(23);
		s23.setSymptomName("您肚子疼吗？");
		abdomenSymptom.add(s23);
		s24.setId(24);
		s24.setSymptomName("您拉肚子吗？");
		abdomenSymptom.add(s24);
		s25.setId(25);
		s25.setSymptomName("您便秘吗？");
		abdomenSymptom.add(s25);
		s26.setId(26);
		s26.setSymptomName("您腹部有肿块吗？");
		abdomenSymptom.add(s26);
		s33.setId(33);
		s33.setSymptomName("您最近有小便次数增多吗？");
		abdomenSymptom.add(s33);
		s34.setId(34);
		s34.setSymptomName("您最近有尿急和尿痛吗？");
		abdomenSymptom.add(s34);
		s35.setId(35);
		s35.setSymptomName("您最近有少尿或无尿吗？");
		abdomenSymptom.add(s35);
		s36.setId(36);
		s36.setSymptomName("您最近有多尿吗？");
		abdomenSymptom.add(s36);
		symptom_h.put("abdomenSymptom", abdomenSymptom);
		//四肢症状
		ArrayList<Symptom> limbSymptom = new ArrayList<Symptom>();
		Symptom s27 = new Symptom();
		Symptom s28 = new Symptom();
		Symptom s29 = new Symptom();
		Symptom s30 = new Symptom();
		Symptom s31 = new Symptom();
		Symptom s32 = new Symptom();
		s27.setId(27);
		s27.setSymptomName("您有关节疼吗？");
		limbSymptom.add(s27);
		s28.setId(28);
		s28.setSymptomName("您有皮肤出血或紫点紫斑吗？");
		limbSymptom.add(s28);
		s29.setId(29);
		s29.setSymptomName("您的胳膊、手、腿、脚有水肿或肿胀吗？");
		limbSymptom.add(s29);
		s30.setId(30);
		s30.setSymptomName("您有胳膊或腿疼吗？");
		limbSymptom.add(s30);
		s31.setId(31);
		s31.setSymptomName("您的胳膊、手、腿、脚有肿块吗？");
		limbSymptom.add(s31);
		s32.setId(32);
		s32.setSymptomName("您的胳膊、手、腿、脚对冷、热、痛感觉变迟钝吗？");
		limbSymptom.add(s32);
		symptom_h.put("limbSymptom", limbSymptom);
	}
}
