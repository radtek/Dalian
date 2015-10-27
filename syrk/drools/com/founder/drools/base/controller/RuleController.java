package com.founder.drools.base.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.founder.drools.base.zdry.service.ZdryRuleService;
import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.RuleConfig;
import com.founder.framework.annotation.RestfulAnnotation;
import com.founder.framework.base.controller.BaseController;
/**
 * ****************************************************************************
 * @Package:      [com.founder.zdrygl.base.controller.RuleTestController.java]  
 * @ClassName:    [RuleTestController]   
 * @Description:  [规则引擎测试控制器]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年10月14日 下午2:34:49]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月14日 下午2:34:49，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("rule")
public class RuleController extends BaseController {					
	
	@Resource(name="zdryRuleService")
	private ZdryRuleService zdryRuleService;		
	
	@Autowired
	private RuleService ruleService;
	
	/**
	 * 
	 * @Title: test
	 * @Description: TODO(规则引擎测试)
	 * @param @param sessionBean
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RestfulAnnotation(serverId="3")
	@RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView test(){			
		ModelAndView mv = new ModelAndView("zdrygl/ruleTest");
		//sessionBean = getSessionBean(sessionBean);
		List list=new LinkedList();
		//通过规则引擎获取消息
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("zdryXm","测试姓名");
		paraMap.put("zdrylx","重点人员类型");
		paraMap.put("fsrUserCode","210203194703112293");
		paraMap.put("fsrOrgCode","210204410102");
		paraMap.put("fsrName","发送人姓名");
		 
		
		Map<String, Object> lgsq = zdryRuleService.getTitleAndContents(ZdryRuleService.LGSQ, paraMap);		
		lgsq.put("rule", "LGSQ");
		list.add(lgsq);		
		
		
		Map<String, Object> CGSQ = zdryRuleService.getTitleAndContents(ZdryRuleService.CGSQ, paraMap);		
		CGSQ.put("rule", "CGSQ");
		list.add(CGSQ);
		
		paraMap.put("spjg","1");	
		Map<String, Object> LGSPJG = zdryRuleService.getTitleAndContents(ZdryRuleService.LGSPJG, paraMap);			
		LGSPJG.put("rule", "LGSPJG_SPTG");
		list.add(LGSPJG);
		
		paraMap.put("spjg","0");		
		Map<String, Object> LGSPJG2 = zdryRuleService.getTitleAndContents(ZdryRuleService.LGSPJG, paraMap);		
		LGSPJG2.put("rule", "LGSPJG_SPBTG");
		list.add(LGSPJG2);
		
		mv.addObject("List",list);		
		return mv;
	
	}		
	
	/**
	 * 
	 * @Title: manager
	 * @Description: TODO(规则管理列表)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RestfulAnnotation(serverId="3")
	@RequestMapping(value = "/manager", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView manager(){			
		ModelAndView mv = new ModelAndView("zdrygl/ruleManager");
		Map<String, RuleConfig> ruleConfigMap = ruleService.getRuleConfigMap();
		if(ruleConfigMap==null) ruleService.init();
		Object[] objAry = ruleConfigMap.keySet().toArray();
		List list=new LinkedList();
		for(int i=0;i<objAry.length;i++){
			Map map=new HashMap();
			map.put("key", objAry[i]);
			map.put("value", ruleConfigMap.get(objAry[i]).getUrl());
			list.add(map);
		}
		mv.addObject("List",list);
		return mv;
	
	}	
	
	/**
	 * 
	 * @Title: reloadOne
	 * @Description: TODO(更新某一组规则)
	 * @param @param str
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throw
	 */
	@RestfulAnnotation(serverId="3")
	@RequestMapping(value = "/reloadOne", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView reloadOne(String str){			
		ModelAndView mv = new ModelAndView("zdrygl/ruleManager");
		ruleService.reLoadOne(str);
		Map<String, RuleConfig> ruleConfigMap = ruleService.getRuleConfigMap();
		Object[] objAry = ruleConfigMap.keySet().toArray();
		List list=new LinkedList();
		for(int i=0;i<objAry.length;i++){
			Map map=new HashMap();
			map.put("key", objAry[i]);
			map.put("value", ruleConfigMap.get(objAry[i]).getUrl());
			list.add(map);
		}
		mv.addObject("List",list);
		return mv;
	
	}	
}