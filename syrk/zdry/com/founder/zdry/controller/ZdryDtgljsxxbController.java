package com.founder.zdry.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.founder.framework.annotation.RestfulAnnotation;
import com.founder.framework.base.controller.BaseController;
import com.founder.framework.base.entity.SessionBean;
import com.founder.framework.components.AppConst;
import com.founder.framework.exception.BussinessException;
import com.founder.framework.utils.DateUtils;
import com.founder.framework.utils.EasyUIPage;
import com.founder.framework.utils.StringUtils;
import com.founder.zdry.bean.ZdryDtgljsxxb;
import com.founder.zdry.bean.ZdryZdryzb;
import com.founder.zdry.service.ZdryDtgljsxxbService;
import com.founder.zdry.service.ZdryZdryzbService;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "zdryDtgljsxxb")
public class ZdryDtgljsxxbController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "zdryDtgljsxxbService")
	private ZdryDtgljsxxbService zdryDtgljsxxbService;

	@Resource(name = "zdryZdryzbService")
	private ZdryZdryzbService zdryZdryzbService;

	/**
	 * @Title: query
	 * @Description: TODO(查询列表页面)
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView query() {
		ModelAndView mv = new ModelAndView("zdry/zdryDtgljsxxbQuery");
		return mv;
	}

	/**
	 * @Title: queryList
	 * @Description: TODO(查询列表数据)
	 * @param @param page
	 * @param @param rows
	 * @param @param entity
	 * @param @param sessionBean
	 * @param @return 设定文件
	 * @return EasyUIPage 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public @ResponseBody
	EasyUIPage queryList(EasyUIPage page,
			@RequestParam(value = "rows", required = false) Integer rows,
			ZdryDtgljsxxb entity, SessionBean sessionBean) {
		page.setPagePara(rows);
		sessionBean = getSessionBean(sessionBean);
		return zdryDtgljsxxbService.queryList(page, entity);
	}

	/**
	 * @Title: add
	 * @Description: TODO(新增页面)
	 * @param @param zdryid
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(String zdryid, SessionBean sessionBean)
			throws BussinessException {
		ModelAndView mv = new ModelAndView("zdry/zdryDtgljsxxb");
		sessionBean = getSessionBean(sessionBean);
		ZdryDtgljsxxb entity = new ZdryDtgljsxxb();
		entity.setZdryid(zdryid);
		entity.setJs_sj(DateUtils.getSystemDateString());
		entity.setDtjsmj_id(sessionBean.getUserId());
		entity.setDtjsmj_xm(sessionBean.getUserName());
		entity.setJslxdm("1");
		mv.addObject("entity", entity);
		ZdryZdryzb zdryZdryzb = zdryZdryzbService.queryById(entity.getZdryid());
		if (zdryZdryzb == null) {
			throw new BussinessException("重点人员总表查询无数据！");
		}
		String zdrygllxdm = zdryZdryzb.getZdrygllxdm();
		String listJson = zdryDtgljsxxbService.getJsmbListJson(zdrygllxdm);
		mv.addObject("listJson", listJson);
		return mv;
	}

	/**
	 * @Title: edit
	 * @Description: TODO(编辑页面)
	 * @param @param id
	 * @param @return
	 * @param @throws BussinessException 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */

	@RestfulAnnotation(serverId = "3")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value = "id") String id,
			SessionBean sessionBean) throws BussinessException {
		if (sessionBean != null
				&& !StringUtils.isBlank(sessionBean.getUserName())) { // 服务调用
			ModelAndView mv = new ModelAndView(getViewName(sessionBean));
			ZdryDtgljsxxb entity = zdryDtgljsxxbService.queryById(id);
			if (entity == null) {
				throw new BussinessException("查询无数据！");
			}
			mv.addObject(AppConst.MESSAGES, new Gson().toJson(entity));
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("zdry/zdryDtgljsxxb");
			ZdryDtgljsxxb entity = zdryDtgljsxxbService.queryById(id);
			if (entity == null) {
				throw new BussinessException("查询无数据！");
			}
			ZdryZdryzb zdryZdryzb = zdryZdryzbService.queryById(entity
					.getZdryid());
			if (zdryZdryzb == null) {
				throw new BussinessException("重点人员总表查询无数据！");
			}
			String zdrygllxdm = zdryZdryzb.getZdrygllxdm();
			String listJson = zdryDtgljsxxbService.getJsmbListJson(zdrygllxdm);
			mv.addObject("listJson", listJson);
			mv.addObject("entity", entity);
			return mv;
		}
	}

	/**
	 * @Title: save
	 * @Description: TODO(新增保存)
	 * @param @param entity
	 * @param @param sessionBean
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RestfulAnnotation(valiField = "zdryid,sfzkdm,dtjs,js_sj,dtjsmj_id,dtjsmj_xm", serverId = "3")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(ZdryDtgljsxxb entity, SessionBean sessionBean) {
		ModelAndView mv = new ModelAndView(getViewName(sessionBean));
		Map<String, Object> map = new HashMap<String, Object>();
		sessionBean = getSessionBean(sessionBean);
		try {
			zdryDtgljsxxbService.save(entity, sessionBean);
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES, getAddSuccess());
			map.put(AppConst.SAVE_ID, "" + entity.getId()); // 返回主键
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES, getAddFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mv;
	}

	/**
	 * @Title: update
	 * @Description: TODO(修改保存)
	 * @param @param entity
	 * @param @param sessionBean
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RestfulAnnotation(valiField = "id,zdryid,sfzkdm,dtjs,js_sj,dtjsmj_id,dtjsmj_xm", serverId = "3")
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST,
			RequestMethod.PUT })
	public ModelAndView update(ZdryDtgljsxxb entity, SessionBean sessionBean) {
		ModelAndView mv = new ModelAndView(getViewName(sessionBean));
		Map<String, Object> map = new HashMap<String, Object>();
		sessionBean = getSessionBean(sessionBean);
		try {
			zdryDtgljsxxbService.update(entity, sessionBean);
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES, getUpdateSuccess());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES, getUpdateFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mv;
	}

	/**
	 * @Title: delete
	 * @Description: TODO(注销保存)
	 * @param @param entity
	 * @param @param sessionBean
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RestfulAnnotation(valiField = "id", serverId = "3")
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public ModelAndView delete(ZdryDtgljsxxb entity, SessionBean sessionBean) {
		ModelAndView mv = new ModelAndView(getViewName(sessionBean));
		Map<String, Object> map = new HashMap<String, Object>();
		sessionBean = getSessionBean(sessionBean);
		try {
			zdryDtgljsxxbService.delete(entity, sessionBean);
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES, getDeleteSuccess());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES, getDeleteFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mv;
	}

}
