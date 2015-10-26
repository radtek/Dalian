package com.founder.drools.core.inteface.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Service;

import com.founder.drools.core.inteface.RuleService;
import com.founder.drools.core.model.RuleConfig;
import com.founder.framework.config.SystemConfig;

/**
 * ****************************************************************************
 * @Package:      [com.founder.zdrygl.core.inteface.impl.RuleServiceImpl.java]  
 * @ClassName:    [RuleServiceImpl]   
 * @Description:  [规则服务实现]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年10月16日 下午5:47:09]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月16日 下午5:47:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service
public class RuleServiceImpl implements RuleService {		
	private Logger logger = Logger.getLogger(this.getClass());
	
	//规则对应关系Map
	private Map<String, RuleConfig> ruleConfigMap = null;
	
	//主要的配置规则
	private RuleConfig mainRuleConfig=new RuleConfig();	
	
	/**
	 * 
	 * @Title: init
	 * @Description: TODO(初始化规则服务的对应关系)
	 * @param     设定文件
	 * @return void    返回类型
	 * @throw
	 */		
	public void init(){
		logger.info("Rule config init");
		String ruleService = SystemConfig.getString("RuleService");
		if(ruleService==null){
			throw new RuntimeException("Can not fond \"RuleService\"");
		}
		
		String ruleServiceUserName = SystemConfig.getString("RuleServiceUserName");
		if(ruleServiceUserName==null){
			throw new RuntimeException("Can not fond \"RuleServiceUserName\"");
		}
		
		String ruleServicePassword = SystemConfig.getString("RuleServicePassword");
		if(ruleServicePassword==null){
			throw new RuntimeException("Can not fond \"RuleServicePassword\"");
		}
		
		String ruleMainPackage = SystemConfig.getString("RuleMainPackage");
		if(ruleMainPackage==null){
			throw new RuntimeException("Can not fond \"RuleMainPackage\"");
		}
		
		mainRuleConfig.setUrl(ruleService+"/packages/"+ruleMainPackage+"/binary");
		mainRuleConfig.setUserName(ruleServiceUserName);
		mainRuleConfig.setUserPassword(ruleServicePassword);
		ruleConfigMap = new HashMap<String, RuleConfig>();
		ruleConfigMap.put("main", mainRuleConfig);
		
		Map<String,Object>	map = new HashMap<String,Object>();		
		if(this.executeRule("main",map,null)){//获取所有的规则URL
			Object[] keyAry = map.keySet().toArray();
			String packageStr;
			for(int i=0;i<keyAry.length;i++){
				packageStr=(String) map.get(keyAry[i]);
				RuleConfig ruleConfig = new RuleConfig(ruleService+"/packages/"+packageStr+"/binary",ruleServiceUserName,ruleServicePassword);
				ruleConfigMap.put(String.valueOf(keyAry[i]), ruleConfig);
			}
		}else{
			ruleConfigMap=null;//下次使用的时候才能重新初始化
		}
	}
		

	@Override
	public boolean executeRule(String ruleServerName, Object paramObj, Map globalParamMap) {
		try{	
			if(ruleConfigMap==null) init();
			if(ruleConfigMap==null) 
				throw new RuntimeException("Can not init rule server config!");
			RuleConfig ruleConfig = (RuleConfig) ruleConfigMap.get(ruleServerName);
			if(ruleConfig == null){
				init();
				if(ruleConfig == null)
					throw new RuntimeException("Can not find rule named \""+ruleServerName+"\"");
			}
			
			StatefulKnowledgeSession ksession = ruleConfig.getKbase().newStatefulKnowledgeSession();
			
			//循环设置全局变量
			if(globalParamMap!=null){
				Object[] keyAry = globalParamMap.keySet().toArray();
				for(int i=0;i<keyAry.length;i++){
					ksession.setGlobal((String)keyAry[i], globalParamMap.get(keyAry[i]));					
				}
				
			}
			
			//循环设置参数
			if(paramObj!=null){
				if(paramObj instanceof List){		
					List list=(List)paramObj;
					for(int i=0;i<list.size();i++){
						ksession.insert(list.get(i));					
					}
				}else{
					ksession.insert(paramObj);		
				}
				
			}
	
	        //触发规则引擎
	        ksession.fireAllRules();
	        ksession.dispose();  
	        return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}


}