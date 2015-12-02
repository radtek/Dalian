package com.founder.zdrygl.base.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.drools.base.zdry.service.ZdryRuleService;
import com.founder.framework.exception.BussinessException;
import com.founder.zdrygl.core.inteface.SysMessageInfoService;
import com.founder.zdrygl.core.model.SysMessage;

/**
 * ****************************************************************************
 * @Package:      [com.founder.zdrygl.base.message.SysMessageInfoRuleServiceImpl.java]  
 * @ClassName:    [SysMessageInfoRuleServiceImpl]   
 * @Description:  [消息规则引擎实现]   
 * @Author:       [wei.wen@founder.com.cn]  
 * @CreateDate:   [2015年10月15日 下午5:16:25]   
 * @UpdateUser:   [wei.wen@founder.com.cn(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2015年10月15日 下午5:16:25，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service("sysMessageInfoService")
public class SysMessageInfoRuleServiceImpl implements SysMessageInfoService {
	@Autowired	
	private ZdryRuleService zdryRuleService;
	
	@Override
	public SysMessage initSysMessage(String xxlx, Object param) {
		
		try{
			Map resMap=zdryRuleService.getZdryMessage(xxlx, param);
			String msgTitle=(String) resMap.get("msgTitle");
			String msgContent=(String) resMap.get("msgContent");
			
			
			SysMessage sysMessage=new SysMessage();
			sysMessage.setXxbt(msgTitle);//信息标题
			sysMessage.setXxnr(msgContent);//信息内容
			
			//还需要添加信息其他内容
			
			return sysMessage;			
		}catch(Exception e){
			throw new BussinessException(e.toString());
		}
		
	}
	
}
