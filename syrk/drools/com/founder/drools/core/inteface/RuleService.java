package com.founder.drools.core.inteface;

import java.util.Map;

/**
 * ****************************************************************************
 * @Package:      [com.founder.zdrygl.core.inteface.RuleService.java]  
 * @ClassName:    [RuleService]   
 * @Description:  [规则服务]   
 * @Author:       [zhang.hai@founder.com.cn]  
 * @CreateDate:   [2015年10月16日 下午5:34:14]   
 * @UpdateUser:   [ZhangHai(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月16日 下午5:34:14，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public interface RuleService {
	
	/**
	 * 
	 * @Title: init
	 * @Description: TODO(初始化规则服务的对应关系)
	 * @param     设定文件
	 * @return void    返回类型
	 * @throw
	 */
	public void init();
	/**
	 * 
	 * @Title: executeRule
	 * @Description: TODO(规则触发，获取对象)
	 * @param @param ruleName 规则名称
	 * @param @param param 规则参数，放置参数的List或者单个参数对象
	 * @param @param globalParam 放有全局变量的Map
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throw
	 */	
	public boolean executeRule(String ruleName, Object paramObj,Map<String, Object> globalParamMap);
	
}