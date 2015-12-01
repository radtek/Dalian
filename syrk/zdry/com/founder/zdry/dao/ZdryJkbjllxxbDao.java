package com.founder.zdry.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.founder.framework.base.dao.BaseDaoImpl;
import com.founder.framework.base.entity.SessionBean;
import com.founder.framework.utils.EasyUIPage;
import com.founder.framework.utils.StringUtils;
import com.founder.zdry.bean.ZdryJkbjllxxb;

/******************************************************************************
 * @Package: [com.founder.zdry.dao.ZdryJkbjllxxbDao.java]
 * @ClassName: [ZdryJkbjllxxbDao]
 * @Description: [重点人员补充信息－监控帮教力量]
 * @Author: [hongguanjun]
 * @CreateDate: [2015-3-12 上午10:07:21]
 * @UpdateUser: [hongguanjun(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2015-3-12 上午10:07:21，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]
 * @Version: [v1.0]
 */
@Repository("zdryJkbjllxxbDao")
public class ZdryJkbjllxxbDao extends BaseDaoImpl {

	/**
	 * 查询列表<br>
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public EasyUIPage queryList(EasyUIPage page, ZdryJkbjllxxb entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		String sort = page.getSort();
		String order = page.getOrder();
		if (StringUtils.isBlank(sort)) { // 默认排序
			sort = "xt_cjsj";
			order = "desc";
		}
		map.put("sort", sort);
		map.put("order", order);
		entity.setJkbjry_gmsfhm(StringUtils.getSqlExpression(entity
				.getJkbjry_gmsfhm()));
		entity.setJkbjry_xm(StringUtils.getSqlExpression(entity.getJkbjry_xm()));
		map.put("zdryJkbjllxxb", entity);
		page.setTotal((Integer) queryForObject("ZdryJkbjllxxb.queryCount", map));
		page.setRows(queryForList("ZdryJkbjllxxb.queryList", map));
		return page;
	}

	/**
	 * 查询列表<br>
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public List<ZdryJkbjllxxb> queryViewList(Map<String, Object> map) {
		List<ZdryJkbjllxxb> list = null;
		if (map != null) {
			String zdryid = (String) map.get("zdryid");
			if (!StringUtils.isBlank(zdryid)) {
				list = queryForList("ZdryJkbjllxxb.queryViewList", map);
			}
		}
		return list;
	}

	/**
	 * @Title: queryById
	 * @Description: TODO(根据ID查询单条记录)
	 * @param @param id
	 * @param @return 设定文件
	 * @return ZdryJkbjllxxb 返回类型
	 * @throws
	 */
	public ZdryJkbjllxxb queryById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		} else {
			return (ZdryJkbjllxxb) queryForObject("ZdryJkbjllxxb.queryById", id);
		}
	}

	/**
	 * 新增<br>
	 * 
	 * @param entity
	 */
	public void save(ZdryJkbjllxxb entity, SessionBean sessionBean) {
		insert("ZdryJkbjllxxb.save", entity);
	}

	/**
	 * 更新<br>
	 * 
	 * @param entity
	 */
	public void update(ZdryJkbjllxxb entity, SessionBean sessionBean) {
		update("ZdryJkbjllxxb.update", entity);
	}

	/**
	 * 删除<br>
	 * 
	 * @param entity
	 */
	public void delete(ZdryJkbjllxxb entity, SessionBean sessionBean) {
		update("ZdryJkbjllxxb.delete", entity);
	}

	/**
	 * @Title: queryBjryIsExist
	 * @Description: TODO(帮教人员是否存在，对于同一个重点人员)
	 * @param @param entity
	 * @param @return 设定文件
	 * @return ZdryJkbjllxxb 返回类型
	 * @throws
	 */
	public boolean existBjry(ZdryJkbjllxxb entity) {
		boolean returnValue = false;
		Integer count = (Integer) queryForObject("ZdryJkbjllxxb.existBjry",
				entity);
		if (count.intValue() > 0) {
			returnValue = true;
		} else {
			returnValue = false;
		}
		return returnValue;
	}

}
