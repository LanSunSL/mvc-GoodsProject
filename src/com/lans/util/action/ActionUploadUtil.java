package com.lans.util.action;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.lans.util.web.ServletContextApplicationUtil;

/**
 * 处理文件上传的工具类，主要实现以下功能
 * <li>创建单个或多个文件名称<br/>
 * <li>保存单个或多个文件<br/>
 * 
 * @author Lan
 *
 */
public class ActionUploadUtil {
	private String directory;
	private String singleFileName;
	private Set<String> multiFileNames;
	private SmartUpload smart = ServletContextApplicationUtil.getSmartUpload();

	// 上传文件保存的文件夹，每个Action对应一个不同的文件夹
	public ActionUploadUtil(String directory) {
		this.directory = directory;
	}

	/**
	 * 使用UUID创建单个文件名称，若没有，则使用nophoto.jpg（只针对图片上传）
	 * 
	 * @return
	 * @throws IOException
	 */
	public String createSingleFileName() throws IOException {
		if (isUpload()) {
			this.singleFileName = UUID.randomUUID() + "." + this.smart.getFiles().getFile(0).getFileExt();
			return this.singleFileName;
		}
		return "nophoto.jpg";
	}

	/**
	 * 创建多个文件名称
	 * 
	 * @return
	 * @throws IOException
	 */
	public Set<String> getMultiFileNames() throws IOException {
		this.multiFileNames = new HashSet<String>();
		if (isUpload()) {
			for (int i = 0; i < smart.getFiles().getCount(); i++) {
				String fileName = UUID.randomUUID() + "." + this.smart.getFiles().getFile(i).getFileExt();
				this.multiFileNames.add(fileName);
			}
		} else {
			this.multiFileNames.add("nophoto.jpg");
		}
		return this.multiFileNames;
	}

	/**
	 * 保存单个文件：先取得要保存文件的保存路径，再使用SmartUpload保存文件
	 * 
	 * @throws IOException
	 * @throws SmartUploadException
	 */
	public void saveSingleFile() throws IOException, SmartUploadException {
		String filePath = ServletContextApplicationUtil.getServletContext()
				.getRealPath("/" + this.directory + "/" + this.singleFileName);
		this.smart.getFiles().getFile(0).saveAs(filePath);
	}

	/**
	 * 保存多个文件：先取得要保存文件的保存路径，再使用SmartUpload保存文件
	 * 
	 * @throws IOException
	 * @throws SmartUploadException
	 */
	public void saveMultiFiles() throws IOException, SmartUploadException {
		Iterator<String> iter = this.multiFileNames.iterator();
		int foot = 0;
		while (iter.hasNext()) {
			String filePath = ServletContextApplicationUtil.getServletContext()
					.getRealPath("/" + this.directory + "/" + iter.next());
			this.smart.getFiles().getFile(foot++).saveAs(filePath);
		}
	}

	public boolean isUpload() throws IOException {
		return this.smart.getFiles().getSize() > 0;
	}
}
