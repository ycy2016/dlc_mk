package com.mas.dtc.po;

/**
 * Mapping文件实例
 * @author Mathsys
 *
 */
public class MappingFileInfo {

	
	/**
	 * id
	 */
	private Integer mappingFileId;
	
	/**
	 * 帐号
	 */
	private String account;
	
	/**
	 * ip地址
	 */
	private String ip;
	
	/**
	 * mapping 文件名称
	 */
    private String fileName;
    
    /**
     * 解析结果
     */
    private String status;

    /**
     * 构造器
     * 
     * @param ip ip 地址
     * @param account  帐号
     * @param fileName 文件名称
     */
    public MappingFileInfo(String ip,String account,String fileName){
    	this.ip = ip;
		this.account = account;
    	this.fileName = fileName;
    }
    
    public String getIp() {
		return ip;
	}
    
	public void setIp(String ip) {
		this.ip = ip;
	}

	
    
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMappingFileId() {
		return mappingFileId;
	}

	public void setMappingFileId(Integer mappingFileId) {
		this.mappingFileId = mappingFileId;
	}

	@Override
	public String toString() {
		return "MappingFileInfo [mappingFileId=" + mappingFileId + ", account=" + account + ", ip=" + ip + ", fileName="
				+ fileName + ", status=" + status + "]";
	}
	
    
    
    
    
}
