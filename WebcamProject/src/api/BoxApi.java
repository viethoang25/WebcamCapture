package api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.box.sdk.*;

public class BoxApi {

	private static BoxApi instance = null;
	public static final String ACCESS_TOKEN = "Q0KPOQHN465PIJJyit7O5l3rU84URffc";
	private BoxAPIConnection api;

	private BoxApi() {
		api = new BoxAPIConnection(ACCESS_TOKEN);
	}

	public static BoxApi getInstance() {
		if (instance == null) {
			instance = new BoxApi();
		}
		return instance;
	}

	public void uploadFile(BoxFolder folder, String file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		folder.uploadFile(stream, file);
		stream.close();
	}

	public void downloadFile(String fileId) throws IOException {
		BoxFile file = new BoxFile(api, fileId);
		BoxFile.Info info = file.getInfo();

		FileOutputStream stream = new FileOutputStream(info.getName());
		file.download(stream);
		stream.close();
	}

	public void deleteFile(String fileId) {
		BoxFile file = new BoxFile(api, fileId);
		file.delete();
	}

	public BoxFolder getBoxFolder(String folderId) {
		return new BoxFolder(api, folderId);
	}

	public BoxFolder getRootFolder() {
		return BoxFolder.getRootFolder(api);
	}

	public List<BoxItem.Info> getFolderItems(BoxFolder folder) {
		List<BoxItem.Info> folderItems = new ArrayList<BoxItem.Info>();
		for (BoxItem.Info itemInfo : folder) {
			folderItems.add(itemInfo);
		}
		return folderItems;
	}

	public BoxFolder.Info createFolder(BoxFolder parentFolder,
			String childFolderName) {
		BoxFolder.Info folderInfo = checkFolderExist(parentFolder,
				childFolderName);
		if (folderInfo == null) {
			BoxFolder.Info childFolderInfo = parentFolder
					.createFolder(childFolderName);
			return childFolderInfo;
		} else {
			return folderInfo;
		}
	}

	public void deleteFolder(String folderId) {
		BoxFolder folder = new BoxFolder(api, folderId);
		folder.delete(true);
	}

	public BoxFolder.Info checkFolderExist(BoxFolder parentFolder,
			String childFolderName) {
		for (BoxItem.Info itemInfo : parentFolder) {
			if (itemInfo instanceof BoxFolder.Info) {
				if (itemInfo.getName().equals(childFolderName))
					return (BoxFolder.Info) itemInfo;
			}
		}
		return null;
	}

}
