package com.ruyicai.wap.controller;

import static com.ruyicai.wap.Global.rbint;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ruyicai.wap.util.CommonUtil;

@RequestMapping("/leaveOut")
@Controller
public class LeaveOutController {
	private static final Logger logger = Logger.getLogger(LeaveOutController.class);
	private static final String prizeData = rbint.getString("prizeData");
	@RequestMapping("/leaveOutDetail.jspx")
	public String leaveOutDetail(
			@RequestParam(value="lotno",defaultValue="") String lotno,
			@RequestParam(value="batchcode",defaultValue="") String batchcode,
			@RequestParam(value="key",defaultValue="") String key,
			@RequestParam(value="type",defaultValue="") String type,
			Model model
			){
		String deadline = CommonUtil.getDeadline(lotno, 0);
		List<Map<String, Object>> leaveOutList = getLeaveOutList(lotno, batchcode,key,type);
//		Map<String, Object> leaveOutMap = getLeaveOutList(lotno, batchcode, getKey(lotno),type);
		String lotName = CommonUtil.getLotnameByLotno(lotno);
//		String typeName = getTypeName(type);
//		model.addAttribute("leaveOutMap", leaveOutMap);
		model.addAttribute("leaveOutList", leaveOutList);
		model.addAttribute("lotName", lotName);
		model.addAttribute("deadline", deadline);
		model.addAttribute("lotno", lotno);
		model.addAttribute("batchcode", batchcode);
		model.addAttribute("type", type);
		return "wap/leaveOut/leaveOutDetail";
	}
	public List<Map<String, Object>> getLeaveOutList(String lotno,String batchcode,String key,String type){
		List<Map<String, Object>> leaveOutList = new ArrayList<Map<String,Object>>();
		String result = getLeaveOut(lotno, batchcode, key);
		JSONObject jsonObject = JSONObject.fromObject(result);
		String errorCode = jsonObject.getString("errorCode");
		if("0".equals(errorCode)){
			JSONObject objectValue = JSONObject.fromObject(jsonObject.getString("value"));
			if(objectValue!=null){
				List<String> leaveOutCodeList = null;
				if ("F47102".equals(lotno)) {//七乐彩
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("miss");
					leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");

					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutMap.put("typeName", "当前期遗漏");
					leaveOutList.add(leaveOutMap);
				}else if ("F47103".equals(lotno)) {//福彩3D
					leaveOutCodeList = new ArrayList<String>();
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					JSONArray arrayMiss = objectValue.getJSONArray("bai");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutMap.put("typeName", "百位当前期遗漏");
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("shi");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutMap.put("typeName", "十位当前期遗漏");
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("ge");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutMap.put("typeName", "个位当前期遗漏");
					leaveOutList.add(leaveOutMap);
				}else if ("F47104".equals(lotno)) {//双色球
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("red");
					leaveOutCodeList= getLeaveOutCodeAddZeroList(arrayMiss, "red");
					leaveOutMap.put("typeName", "<a style='color: red'>红球</a>当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("blue");
					leaveOutCodeList= getLeaveOutCodeAddZeroList(arrayMiss, "blue");
					leaveOutMap.put("typeName", "<a style='color: blue'>蓝球</a>当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);

				}else if ("T01001".equals(lotno)) {//大乐透
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("qian");
					leaveOutCodeList= getLeaveOutCodeAddZeroList(arrayMiss, "red");
					leaveOutMap.put("typeName", "前区当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("hou");
					leaveOutCodeList= getLeaveOutCodeAddZeroList(arrayMiss, "blue");
					leaveOutMap.put("typeName", "后区当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
				}else if ("T01002".equals(lotno)) {//排列三
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("bai");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "百位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("shi");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "十位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("ge");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "个位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
				}else if ("T01007".equals(lotno)) {//时时彩
					if("T01007MV_5X".equals(key)){
						Map<String, Object> leaveOutMap = new HashMap<String, Object>();
						JSONArray arrayMiss = new JSONArray();
						if("WXZhX".equals(type)||"WXTX".equals(type)){
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("wan");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"万位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
							
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("qian");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"千位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
							
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("bai");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"百位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
							
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("shi");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"十位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
							
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("ge");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"个位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
						}else if("SXZhX".equals(type)){
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("bai");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"百位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
							
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("shi");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"十位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
							
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("ge");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"个位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
						}else if("EXZhX".equals(type)){
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("shi");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"十位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
							
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("ge");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"个位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
						}else if("YX".equals(type)){
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							arrayMiss = objectValue.getJSONArray("ge");
							leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
							leaveOutMap.put("typeName", getTypeName(type)+"个位当前期遗漏");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutList.add(leaveOutMap);
						}
						
						
					}else if("T01007MV_DD".equals(key)){
						Map<String, Object> leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						JSONArray arrayMiss = objectValue.getJSONArray("geDX");
						String leaveOutCodeView = "";
						for(int i=0;i<arrayMiss.size();i++){
							String leaveOutCode = arrayMiss.get(i).toString();
							if(leaveOutCode.length()==1){
								leaveOutCode = "_"+leaveOutCode;
							}
							String viewName="";
							if(i==0){
								viewName= "大";
							}else if(i==1){
								viewName= "小";
							}else if(i==2){
								viewName= "单";
							}else if(i==3){
								viewName= "双";
							}
							leaveOutCodeView = "["+viewName+"]->"+leaveOutCode;
							leaveOutCodeList.add(leaveOutCodeView);
							
						}
						leaveOutMap.put("typeName", getTypeName(type)+"个位当前期遗漏");
						leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
						leaveOutList.add(leaveOutMap);
						
						leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						arrayMiss = objectValue.getJSONArray("shiDX");
						leaveOutCodeView = "";
						for(int i=0;i<arrayMiss.size();i++){
							String leaveOutCode = arrayMiss.get(i).toString();
							if(leaveOutCode.length()==1){
								leaveOutCode = "_"+leaveOutCode;
							}
							String viewName="";
							if(i==0){
								viewName= "大";
							}else if(i==1){
								viewName= "小";
							}else if(i==2){
								viewName= "单";
							}else if(i==3){
								viewName= "双";
							}
							leaveOutCodeView = "["+viewName+"]->"+leaveOutCode;
							leaveOutCodeList.add(leaveOutCodeView);
							
						}
						leaveOutMap.put("typeName", getTypeName(type)+"十位当前期遗漏");
						leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
						leaveOutList.add(leaveOutMap);
					}else if("T01007MV_2ZXHZ".equals(key)){
						Map<String, Object> leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						JSONArray arrayMiss = objectValue.getJSONArray("miss");
						leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
						leaveOutMap.put("typeName", getTypeName(type)+"当前期遗漏");
						leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
						leaveOutList.add(leaveOutMap);
					}else if("T01007MV_2ZX".equals(key)){
						Map<String, Object> leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						JSONArray arrayMiss = objectValue.getJSONArray("miss");
						leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
						leaveOutMap.put("typeName", getTypeName(type)+"当前期遗漏");
						leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
						leaveOutList.add(leaveOutMap);
					}
					
				}else if ("T01009".equals(lotno)) {//七星彩
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("baiwan");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "第一位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("shiwan");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "第二位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("wan");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "第三位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("qian");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "第四位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("bai");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "第五位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("shi");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "第六位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("ge");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "第七位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
				}else if ("T01010".equals(lotno)) {//江西11选5/多乐彩
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("miss");
					leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutMap.put("typeName", getTypeName(type)+"当前期遗漏");
					leaveOutList.add(leaveOutMap);
				}else if ("T01011".equals(lotno)) {//排列五
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("wan");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "万位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("qian");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "千位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("bai");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "百位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("shi");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "十位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
					
					leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					arrayMiss = objectValue.getJSONArray("ge");
					leaveOutCodeList = getLeaveOutCodeList(arrayMiss);
					leaveOutMap.put("typeName", "个位当前期遗漏");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutList.add(leaveOutMap);
				}else if ("T01012".equals(lotno)) {//十一运夺金/山东11选五
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("miss");
					leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutMap.put("typeName", getTypeName(type)+"当前期遗漏");
					leaveOutList.add(leaveOutMap);
				
				}else if ("T01013".equals(lotno)) {//22选5
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("miss");
					leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutMap.put("typeName", "当前期遗漏");
					leaveOutList.add(leaveOutMap);
				}else if ("T01014".equals(lotno)) {//山东11选5
					Map<String, Object> leaveOutMap = new HashMap<String, Object>();
					leaveOutCodeList = new ArrayList<String>();
					JSONArray arrayMiss = objectValue.getJSONArray("miss");
					leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
					leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
					leaveOutMap.put("typeName", "当前期遗漏");
					leaveOutList.add(leaveOutMap);
				}else if ("T01015".equals(lotno)) {//山东快乐10分
					if("T01015MV_S1".equals(key)){
						Map<String, Object> leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						JSONArray arrayMiss = objectValue.getJSONArray("miss");
						leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
						leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
						leaveOutMap.put("typeName", "当前期遗漏");
						leaveOutList.add(leaveOutMap);

					}
					if("T01015MV_Q3".equals(key)){
						Map<String, Object> leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						JSONArray arrayMiss = null;
//						leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
						leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						arrayMiss = objectValue.getJSONArray("bai");
						leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
						leaveOutMap.put("typeName", "百位当前期遗漏");
						leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
						leaveOutList.add(leaveOutMap);
						
						leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						arrayMiss = objectValue.getJSONArray("shi");
						leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
						leaveOutMap.put("typeName", "十位当前期遗漏");
						leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
						leaveOutList.add(leaveOutMap);
						
						leaveOutMap = new HashMap<String, Object>();
						leaveOutCodeList = new ArrayList<String>();
						arrayMiss = objectValue.getJSONArray("ge");
						leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
						leaveOutMap.put("typeName", "个位当前期遗漏");
						leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
						leaveOutList.add(leaveOutMap);
					}
					if("T01015MV_RX".equals(key)){
						if("Q2".equals(type)){
							Map<String, Object> leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							
							JSONArray arrayMiss = objectValue.getJSONArray("miss");
							leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutMap.put("typeName", "前位当前期遗漏");
							leaveOutList.add(leaveOutMap);
							leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutMap.put("typeName", "后位当前期遗漏");
							leaveOutList.add(leaveOutMap);
						}else{
							Map<String, Object> leaveOutMap = new HashMap<String, Object>();
							leaveOutCodeList = new ArrayList<String>();
							JSONArray arrayMiss = objectValue.getJSONArray("miss");
							leaveOutCodeList = getLeaveOutCodeAddZeroList(arrayMiss,"black");
							leaveOutMap.put("leaveOutCodeList", leaveOutCodeList);
							leaveOutMap.put("typeName", "当前期遗漏");
							leaveOutList.add(leaveOutMap);
						}
					}
				}
			}
			
		}
		return leaveOutList;
	}
	public List<String> getLeaveOutCodeAddZeroList(JSONArray arrayMiss,String colorName){
		List<String> leaveOutCodeList = new ArrayList<String>();
		String leaveOutCodeView = "";
		for(int i=0;i<arrayMiss.size();i++){
			String leaveOutCode = arrayMiss.get(i).toString();
			if(leaveOutCode.length()==1){
				leaveOutCode = "_"+leaveOutCode;
			}
			if(i<9){
				leaveOutCodeView = "<a style='color: "+colorName+"'>[0"+(i+1)+"]</a>→"+leaveOutCode;
			}else{
				leaveOutCodeView = "<a style='color: "+colorName+"'>["+(i+1)+"]</a>→"+leaveOutCode;

			}
			leaveOutCodeList.add(leaveOutCodeView);
		}
		return leaveOutCodeList;
	}
	public List<String> getLeaveOutCodeList(JSONArray arrayMiss){
		List<String> leaveOutCodeList = new ArrayList<String>();
		String leaveOutCodeView = "";
		for(int i=0;i<arrayMiss.size();i++){
			String leaveOutCode = arrayMiss.get(i).toString();
			if(leaveOutCode.length()==1){
				leaveOutCode = "_"+leaveOutCode;
			}
			leaveOutCodeView = "["+i+"]→"+leaveOutCode;
			leaveOutCodeList.add(leaveOutCodeView);
		}
		return leaveOutCodeList;
	}
	/**
	 * 查询遗漏调用后台接口
	 * @param lotno彩种
	 * @param batchcode期号
	 * @param key遗漏玩法
	 * @return
	 */
	public String getLeaveOut(String lotno,String batchcode,String key){
		String url = prizeData+"select/missvalue2nd";
		String parameter = "?lotno="+lotno+"&batchcode="+batchcode+"&key="+key;
		logger.info("查询遗漏调用后台接口:"+url+parameter);
		String result = setURLBySET(url+parameter);
		logger.info("查询遗漏调用后台接口返回:"+result);
		return result;
	}
	public static String setURLBySET(String url) {
		HttpClient httpclient = new HttpClient();
		String response = "";
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new  DefaultHttpMethodRetryHandler());
		try {
			int code = httpclient.executeMethod(getMethod);
			if (code == HttpStatus.SC_OK) {
				InputStream resStream = getMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
				StringBuffer resBuffer = new StringBuffer();
				String resTemp = "";
				while((resTemp = br.readLine()) != null){
				resBuffer.append(resTemp);
				}
				response = resBuffer.toString(); 
			} else {
				logger.error("不是200ok返回=code="+code+"url="+url);
			}
		} catch (Exception e) {
			logger.error("setURLBySET异常!", e);
		} finally {
			getMethod.releaseConnection();
		}
		return response;
	}
//	public String getKey(String lotno){
//		String key = "";
//		if ("F47102".equals(lotno)) {//七乐彩
//			/**
//			 * F47102MV_X 选号区为1-30的所有玩法
//			 * {"miss":[0,4,2,3,0,1,7,15,1,0,6,1,5,3,4,2,2,0,1,2,1,9,3,0,0,4,1,6,0,12]}
//			 */
//			key = "F47102MV_X";
//		}else if ("F47103".equals(lotno)) {//福彩3D
//			/**
//			 * F47103MV_ZX 3D直选，选号区为3行0-9
//			 * {"bai":[1,20,15,0,5,8,4,7,2,14],"ge":[11,9,0,5,10,1,6,12,2,16],"shi":[0,38,6,1,4,12,10,3,15,5]}
//			 */
//			key="F47103MV_ZX";
//		}else if ("F47104".equals(lotno)) {//双色球
//			/**
//			 * F47104MV_X 所有采用红球+蓝球的选号区的玩法
//			 * {"blue":[2,5,11,33,13,18,47,22,32,8,39,4,0,3,23,17],
//			 *  "red":[4,2,3,3,3,0,1,14,1,1,4,1,7,10,5,7,3,4,0,9,0,5,11,7,9,0,4,2,2,5,1,0,0]}
//			 */
//			key="F47104MV_X";
//		}else if ("T01001".equals(lotno)) {//大乐透
//			/**
//			 * T01001MV_X  选号区为前区1-35加后区1-12的所有玩法
//			 * {"hou":[2,9,7,16,0,6,0,8,1,1,2,5],
//			 * "qian":[5,5,4,3,3,1,9,2,18,6,6,10,3,1,17,7,3,20,18,1,12,4,26,7,0,0,4,9,0,2,2,0,5,0,1]}
//			 */
//			key = "T01001MV_X";
//		}else if ("T01002".equals(lotno)) {//排列三
//			/**
//			 * T01002MV_ZX 排列三直选，选号区为3行0-9
//			 * {"bai":[0,21,19,8,3,1,10,4,2,23],"ge":[56,2,11,6,5,1,7,0,4,25],"shi":[22,14,9,1,2,0,3,4,10,7]}
//			 */
//			key = "T01002MV_ZX";
//		}else if ("T01007".equals(lotno)) {//时时彩
//			/**
//			 * T01007MV_5X 时时彩1、2、3、5星，五星通选
//			 * {"bai":[4,0,18,1,11,3,2,15,7,5],"ge":[3,1,5,44,2,17,0,13,8,4],"qian":[5,17,4,3,63,1,0,11,7,6],
//			 * "shi":[20,23,9,3,0,7,2,11,10,1],"wan":[10,0,8,5,26,35,9,3,4,1]}
//			 */
//			key = "T01007MV_5X";
//		}else if ("T01009".equals(lotno)) {//七星彩
//			/**
//			 * T01009MV_ZX 选号区为7行0-9的所有玩法
//			 * {"bai":[15,6,23,0,2,3,8,5,1,18],"baiwan":[54,9,0,7,6,4,3,17,2,5],"ge":[9,4,12,2,0,3,17,1,15,40],"qian":[0,12,14,7,1,8,4,9,11,25],
//			 * "shi":[1,6,11,10,4,19,2,0,42,9],"shiwan":[11,7,2,3,19,1,4,16,25,0],"wan":[9,0,1,10,11,14,4,5,12,3]}
//			 */
//			key = "T01009MV_ZX";
//		}else if ("T01010".equals(lotno)) {//江西11选5/多乐彩
//			
//		}else if ("T01011".equals(lotno)) {//排列五
//			/**
//			 * T01011MV_ZX 选号区为5行0-9的所有玩法
//			 * {"bai":[1,18,21,3,2,0,29,5,20,10],"ge":[6,7,4,5,0,3,38,29,8,1],"qian":[18,1,0,38,36,10,9,6,3,2],
//			 * "shi":[0,45,8,7,3,2,1,5,23,15],"wan":[11,9,1,0,5,8,2,4,27,15]}
//			 */
//			key = "T01011MV_ZX";
//		}else if ("T01012".equals(lotno)) {//十一运夺金/山东11选五
//		}else if ("T01013".equals(lotno)) {//22选5
//			/**
//			 * T01013MV_X 选号区为1-22的所有玩法
//			 * {"miss":[3,3,1,1,4,4,6,2,9,5,1,2,0,0,0,1,4,10,0,2,0,6]}
//			 */
//			key = "T01013MV_X";
//		}
//		return key;
//	}
	public String getTypeName(String type){
		String typeName = "";
		if("rx2".equals(type)){
			typeName = "<a style='color: red'>任选二</a>";
		}else if("rx3".equals(type)){
			typeName = "<a style='color: red'>任选三</a>";
		}else if("rx4".equals(type)){
			typeName = "<a style='color: red'>任选四</a>";
		}else if("rx5".equals(type)){
			typeName = "<a style='color: red'>任选五</a>";
		}else if("rx6".equals(type)){
			typeName = "<a style='color: red'>任选六</a>";
		}else if("rx7".equals(type)){
			typeName = "<a style='color: red'>任选七</a>";
		}else if("rx8".equals(type)){
			typeName = "<a style='color: red'>任选八</a>";
		}else if("WXZhX".equals(type)){
			typeName = "<a style='color: red'>五星直选</a>";
		}else if("WXTX".equals(type)){
			typeName = "<a style='color: red'>五星通选</a>";
		}else if("SXZhX".equals(type)){
			typeName = "<a style='color: red'>三星直选</a>";
		}else if("EXZhX".equals(type)){
			typeName = "<a style='color: red'>二星直选</a>";
		}else if("EXHZh".equals(type)){
			typeName = "<a style='color: red'>二星和值</a>";
		}else if("EXZX".equals(type)){
			typeName = "<a style='color: red'>二星组选</a>";
		}else if("DXDS".equals(type)){
			typeName = "<a style='color: red'>大小单双</a>";
		}else if("YX".equals(type)){
			typeName = "<a style='color: red'>一星</a>";
		}else {
			typeName = "";
		}
		return typeName;
	}
}