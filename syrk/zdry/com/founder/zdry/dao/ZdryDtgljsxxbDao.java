package com.founder.zdry.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.founder.framework.base.dao.BaseDaoImpl;
import com.founder.framework.base.entity.SessionBean;
import com.founder.framework.utils.EasyUIPage;
import com.founder.framework.utils.StringUtils;
import com.founder.zdry.bean.ZdryDtgljsxxb;
import com.founder.zdry.bean.ZdryLczywblb;

/******************************************************************************
 * @Package: [com.founder.zdry.dao.ZdryDtgljsxxbDao.java]
 * @ClassName: [ZdryDtgljsxxbDao]
 * @Description: [重点人员补充信息－动态管理纪实]
 * @Author: [hongguanjun]
 * @CreateDate: [2015-3-12 上午10:07:21]
 * @UpdateUser: [hongguanjun(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2015-3-12 上午10:07:21，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]
 * @Version: [v1.0]
 */
@Repository("zdryDtgljsxxbDao")
public class ZdryDtgljsxxbDao extends BaseDaoImpl {

	/**
	 * 查询列表<br>
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public EasyUIPage queryList(EasyUIPage page, ZdryDtgljsxxb entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		String sort = page.getSort();
		String order = page.getOrder();
		if (StringUtils.isBlank(sort)) { // 默认排序
			sort = "js_sj";
			order = "desc";
		}
		map.put("sort", sort);
		map.put("order", order);
		map.put("zdryDtgljsxxb", entity);
		page.setTotal((Integer) queryForObject("ZdryDtgljsxxb.queryCount", map));
		page.setRows(queryForList("ZdryDtgljsxxb.queryList", map));
		return page;
	}

	/**
	 * 查询列表<br>
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public List<ZdryDtgljsxxb> queryViewList(Map<String, Object> map) {
		List<ZdryDtgljsxxb> list = null;
		if (map != null) {
			String zdryid = (String) map.get("zdryid");
			if (!StringUtils.isBlank(zdryid)) {
				list = queryForList("ZdryDtgljsxxb.queryViewList", map);
			}
		}
		return list;
	}

	/**
	 * @Title: queryById
	 * @Description: TODO(根据ID查询单条记录)
	 * @param @param id
	 * @param @return 设定文件
	 * @return ZdryDtgljsxxb 返回类型
	 * @throws
	 */
	public ZdryDtgljsxxb queryById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		} else {
			return (ZdryDtgljsxxb) queryForObject("ZdryDtgljsxxb.queryById", id);
		}
	}

	/**
	 * 新增<br>
	 * 
	 * @param entity
	 */
	public void save(ZdryDtgljsxxb entity, SessionBean sessionBean) {
		insert("ZdryDtgljsxxb.save", entity);
	}

	/**
	 * 更新<br>
	 * 
	 * @param entity
	 */
	public void update(ZdryDtgljsxxb entity, SessionBean sessionBean) {
		update("ZdryDtgljsxxb.update", entity);
	}

	/**
	 * 删除<br>
	 * 
	 * @param entity
	 */
	public void delete(ZdryDtgljsxxb entity, SessionBean sessionBean) {
		update("ZdryDtgljsxxb.delete", entity);
	}

}
