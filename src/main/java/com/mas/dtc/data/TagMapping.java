/**
 * 
 */
package com.mas.dtc.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mas.dtc.dao.IMappingFileDAO;
import com.mas.dtc.po.MappingFileInfo;
import com.mas.dtc.utils.ExcelPoiUtil;

/**
 * 用于管理标签映射关系的类
 * 
 * @author mas_yu
 *
 */
@Component(value = "tagMapping")
public class TagMapping {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(TagMapping.class);
	/**
	 * 接口数据详情
	 */
	private List<Map<String, String>> interfaceDescList = new ArrayList<Map<String, String>>();

	/**
	 * 按接口代码分的标签详情
	 */
	private Map<String, List<Map<String, Object>>> tagDescByInterfaceId = new HashMap<String, List<Map<String, Object>>>();

	/**
	 * 标签详情
	 */
	private List<Map<String, Object>> tagDescList = new ArrayList<Map<String, Object>>();

	/**
	 * 数策标签
	 */
	public List<Map<String, String>> masTag3List = new ArrayList<Map<String, String>>();
	/**
	 * 数策三四级标签
	 */
	public List<Map<String, String>> masTag4List = new ArrayList<Map<String, String>>();

	/**
	 * 供应商的三四级标签对应
	 */
	public List<Map<String, String>> tag4MapList = new ArrayList<Map<String, String>>();

	/**
	 * 数据源匹配率
	 */
	public List<Map<String, String>> sourceMatchRangeMapList = new ArrayList<Map<String, String>>();

	/**
	 * 路径
	 */
	@Value("${dlc.master.conf}")
	private String basePath;

	/**
	 * 用户操作DAO
	 */
	@Resource
	private IMappingFileDAO mappingFileDAO;

	/**
	 * 对象实例化后,执行的第一个方法
	 */
	@PostConstruct
	public void initFeild() {
		String fileCompletePath = getFilePath();
		loadFeild(fileCompletePath);
		LOG.info("load mapping file is finished");
	}

	/**
	 * 
	 * 解析文件
	 * 
	 * @param mappingFileInfo 文件实例
	 * @return 是否成功,"1" 代表解析成功,"0"代表解析失败
	 */
	public int uploadMapping(MappingFileInfo mappingFileInfo) {

		int status = 1;
		try {
			// 解析和加载数据
			loadFeild(basePath + File.separator + mappingFileInfo.getFileName());
		} catch (Exception e) {
			// 失败
			status = 0;
			e.printStackTrace();
			LOG.error(" PARSE FAILURE ...  " + e.getMessage());
		}
		try {
			// 生成文件表记录
			mappingFileInfo.setStatus(String.valueOf(status));
			mappingFileDAO.insertFileinfo(mappingFileInfo);
		} catch (Exception e) {
			// 失败
			//status = 0;
			LOG.error(e.getMessage(),e);
		}

		if (status != 1) {
			// 失败的话,加载上一次成功的mapping表
			LOG.error(" PARSE FAILURE ,loadding nearly mapping file ");
			String fileCompletePath = getFilePath();
			loadFeild(fileCompletePath);
		}

		return status;
	}

	/**
	 * 通过接口代号获取标签数据
	 * 
	 * @param interfaceid
	 *            标签代码
	 * @return json格式,例如 "data":[ { "Tag_3rd" : "消费笔数品类TOP5分布", "Tag_1st" :
	 *         "消费支付", "Sample" : [ "000100002001002339", "000100002001002340",
	 *         "000100002001002341", "000100002001002342" ], "tag_rate" : "22%",
	 *         "MAS Relation" : "对应", "src_desc" : "取用户近12个月的app消费总和" }]
	 */
	public List<Map<String, Object>> getTagByInterfaceId(String interfaceid) {
		List<Map<String, Object>> tagDescList = this.tagDescByInterfaceId.get(interfaceid);
		return tagDescList;
	}

	/**
	 * 
	 * 通过记录表,查询最近一次解析成功的mapping表文件
	 * 
	 * @return null代表文件不存在
	 */
	public String getFilePath() {
		// 获取最近一次解析成功的mapping文件
		String fileName = mappingFileDAO.getRecentlyFileName();
		String basePath = this.basePath;
		String fileCompletePath = basePath + File.separator + fileName;
		File file = new File(fileCompletePath);
		if (file.isFile() && file.exists()) {
			LOG.info("find mapping file : " + fileCompletePath);
		} else {
			fileCompletePath = null;
			LOG.error("can't find mapping file : " + fileCompletePath);
		}
		return fileCompletePath;
	}

	/**
	 * 
	 * 实例化对象后,执行该方法
	 * 
	 * @param fileCompletePath
	 *            文件名字
	 */
	public void loadFeild(String fileCompletePath) {

		if (fileCompletePath == null) {
			// 文件不存在就不需要加载数据
			return;
		}
		FileInputStream fis;
		XSSFWorkbook workbook = null;
		try {

			fis = new FileInputStream(fileCompletePath);
			 workbook = new XSSFWorkbook(fis);
			loadSheet(workbook);

			/*
			 * 打印----- for (Map<String, String> map : interfaceDescList) {
			 * LOG.info("interfaceDescList:" + map); }
			 * 
			 * for (Map<String, String> map : masTag3List) {
			 * LOG.info("masTag3List:" + map); }
			 * 
			 * for (Map<String, Object> map : tagDescList) {
			 * LOG.info("tagDescList:" + map); }
			 * 
			 * 打印-----
			 */
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(),e);
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}finally{
			try {
				if(workbook!=null){
					workbook.close();
				}
			} catch (IOException e) {
				LOG.error(e.getMessage(),e);
			}
		}

	}

	/**
	 * 加载用到的sheeet
	 * 
	 * @param workbook
	 *            workbook
	 */
	private void loadSheet(XSSFWorkbook workbook) {
		//加载数据前,需要把之前产生的数据清空
		clearFiled();
		int sheetNum = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			List<Map<String, String>> list = null;
			String sheetName = sheet.getSheetName();
			if ("数策标签".equals(sheetName)) {
				list = masTag3List;
			} else if ("数策三四级标签对应".equals(sheetName)) {
				list = masTag4List;
			} else if ("供应商Mapping".equals(sheetName)) {
				list = tag4MapList;
			} else if ("数据源匹配率".equals(sheetName)) {
				list = sourceMatchRangeMapList;
			} else if ("对外服务接口".equals(sheetName)) {
				list = interfaceDescList;
			} else {
				continue;
			}
			initListFromSheet(list, 2, sheet);
		}
		// 加载标签数据
		loadTagDescList();
	}

	/**
	 * 清楚成员属性的内存
	 */
	private void clearFiled() {
		this.masTag3List.clear();
		this.masTag4List.clear();
		this.tag4MapList.clear();
		this.sourceMatchRangeMapList.clear();
		this.interfaceDescList.clear();
		this.tagDescList.clear();
		this.tagDescByInterfaceId.clear();
	}

	/**
	 * 从sheet工作表中读取数据加载到对应的List集合中去
	 * 
	 * @param list
	 *            需要加载的List
	 * @param rowIndex
	 *            excel记录数据起始行
	 * @param sheet
	 *            数据来源工作表
	 */
	private void initListFromSheet(List<Map<String, String>> list, int rowIndex, XSSFSheet sheet) {
		int rowEnd = sheet.getLastRowNum();
		// 加载表格头
		Row fieldRow = sheet.getRow(0);
		int collTotal = fieldRow.getLastCellNum();
		String[] fields = new String[collTotal];
		for (int k = 0; k < collTotal; k++) {
			Cell cell = fieldRow.getCell(k);
			if (null == cell) {
				LOG.error("loading sheet header error, CELL Ojbect is null at sheet:" + sheet.getSheetName() + " row "
						+ 0 + " and cell " + k);
				continue;
			}
			String value = ExcelPoiUtil.getCellValueString(cell);
			if (value == null) {
				LOG.error("loading sheet header error,get 'null' value from cell at sheet: " + sheet.getSheetName()
						+ " row " + 0 + " and cell " + k);
			} else {
				fields[k] = value;
			}
		}

		LOG.info(sheet.getSheetName() + " header is " + Arrays.toString(fields));
		// 加载sheet中的每行记录
		for (int j = rowIndex; j <= rowEnd; j++) {
			Row row = sheet.getRow(j);
			if (null == row) {
				break;
			}
			Map<String, String> rowInfo = new HashMap<String, String>();
			for (int k = 0; k < collTotal; k++) {
				String field = fields[k];
				Cell cell = row.getCell(k);
				String value = null;
				if (null == cell) {
					LOG.error("loading sheet ROW error,CELL Ojbect is null at sheet: " + sheet.getSheetName() + " row "
							+ j + " and cell " + k);
				} else {
					value = ExcelPoiUtil.getCellValueString(cell);
				}

				if (value != null) {
					rowInfo.put(field, value);
				} else {
					// LOG.error("loading sheet ROW error,get 'null' value from
					// CELL at sheet: " + sheet.getSheetName() + " row " + j + "
					// and cell " + k);
					continue;
				}

			}
			// 加载状态为开启的数据
			String status = String.valueOf(rowInfo.get(TagMapField.STATUS)).trim();
			if ("开启".equals(status)) {
				list.add(rowInfo);
			}
		}
	}

	/**
	 * 加载tagDescByInterfaceId集合,为标签数据做准备
	 */
	private void loadTagDescList() {
		// 数策标签
		for (Map<String, String> masTag3 : this.masTag3List) {
			String interfaceIds = masTag3.get(TagMapField.INTERFACE_ID);
			if (interfaceIds != null) {
				//"A1,A2"不同场景,名称的相同标签,对应不同的的匹配率
				int  tagRateArrIndex = 0;
				// 把"A1,A2"拆开分别放入tagDescList
				for (String interfaceId : interfaceIds.split(",")) {
					String sample = masTag3.get(TagMapField.SAMPLE);
					String threedCode = masTag3.get(TagMapField.CODE_3RD);
					List<String> sampleList = new ArrayList<String>();
					// 标签值样例(Sample)用list集合存放,如果是离散直接放,如果是对应,把对应的所以值全部找出来放入list集合中
					if ("对应".equals(masTag3.get(TagMapField.MAS_RELATION))) {
						sampleList.addAll(getSample(threedCode));
					} else {
						sampleList.add(sample);
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(TagMapField.TAG_3RD, masTag3.get(TagMapField.TAG_3RD));
					map.put(TagMapField.TAG_1ST, masTag3.get(TagMapField.TAG_1ST));
//					map.put(TagMapField.TAG_RATE, masTag3.get(TagMapField.TAG_RATE));
					
					if(masTag3.get(TagMapField.TAG_RATE)!=null){
						String tagRate = String.valueOf(masTag3.get(TagMapField.TAG_RATE));
						String[] tagRateArr= tagRate.split(",");
						if(tagRateArrIndex+1 <= tagRateArr.length){
							map.put(TagMapField.TAG_RATE, tagRateArr[tagRateArrIndex]);
						}
					} 
					
					map.put(TagMapField.MAS_RELATION, masTag3.get(TagMapField.MAS_RELATION));
					map.put(TagMapField.SRC_DESC, masTag3.get(TagMapField.SRC_DESC));
					map.put(TagMapField.INTERFACE_ID, interfaceId);
					map.put(TagMapField.SAMPLE, sampleList);
					this.tagDescList.add(map);
					// tagDescByInterfaceId.put(interfaceId, value)
					List<Map<String, Object>> tagDescList = this.tagDescByInterfaceId.get(interfaceId);
					if (tagDescList != null) {
						tagDescList.add(map);
					} else {
						tagDescList = new ArrayList<Map<String, Object>>();
						tagDescList.add(map);
						this.tagDescByInterfaceId.put(interfaceId, tagDescList);
					}
					tagRateArrIndex++;
				}
			}
		}
	}

	/**
	 * 
	 * 从三四级标签集合中取所有对应值
	 * 
	 * @param threedCode
	 *            三级标签code
	 * @return 返回一个ArrayList<String>实例,如: ["20%以下","20%~50%"],可能size为0
	 *
	 */
	public List<String> getSample(String threedCode) {
		List<String> list = new ArrayList<String>();
		if (threedCode != null) {
			for (Map<String, String> tag4Map : masTag4List) {
				if (threedCode.equals(tag4Map.get(TagMapField.CODE_3RD))) {
					list.add(tag4Map.get(TagMapField.TAG_4TH));
				}
			}
		}
		return list;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public List<Map<String, String>> getInterfaceDescList() {
		return interfaceDescList;
	}

	public void setInterfaceDescList(List<Map<String, String>> interfaceDescList) {
		this.interfaceDescList = interfaceDescList;
	}

}
