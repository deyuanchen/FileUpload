package com.zhangzhihao.FileUpload.Java.Utils;


import com.zhangzhihao.FileUpload.Java.Model.File;
import com.zhangzhihao.FileUpload.Java.Model.UploadInfo;
import com.zhangzhihao.FileUpload.Java.Service.FileService;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.zhangzhihao.FileUpload.Java.Utils.MergeFile.mergeFile;

public class IsAllUploaded {

    private final static List<UploadInfo> uploadInfoList = new ArrayList<>();
    private final static Map<Integer,UploadInfo> hashmap = new HashMap<>();

    /**
     * @param md5
     * @param chunks
     * @return
     */
    public static synchronized boolean isAllUploaded(String md5,
                                        String chunks,String chunk,String uploadFolderPath, String fileName, String ext) {
        if (md5!=null){
            /*uploadInfoList.stream()
                    .forEach(item -> item.getMd5().equals(md5));*/


            System.out.println("hashmap.size()-->"+hashmap.size());

            boolean bool = hashmap.size() == Integer.parseInt(chunks);
            if (bool){
                synchronized (hashmap) {
                    hashmap.clear();
                    return true;
                }
            }
        }


        return false;
    }

    /**
     * @param md5         MD5
     * @param guid        随机生成的文件名
     * @param chunk       文件分块序号
     * @param chunks      文件分块数
     * @param fileName    文件名
     * @param ext         文件后缀名
     * @param fileService fileService
     */
    public static void Uploaded(@NotNull final String md5,
                                @NotNull final String guid,
                                @NotNull final String chunk,
                                @NotNull final String chunks,
                                @NotNull final String uploadFolderPath,
                                @NotNull final String fileName,
                                @NotNull final String ext,
                                @NotNull final FileService fileService)
            throws Exception {
        synchronized (uploadInfoList) {
            uploadInfoList.add(new UploadInfo(md5, chunks, chunk, uploadFolderPath, fileName, ext));
            System.out.println("chunk-->"+chunk);
            hashmap.put(Integer.parseInt(chunk),new UploadInfo(md5, chunks, chunk, uploadFolderPath, fileName, ext) );

        }
        boolean allUploaded = isAllUploaded(md5, chunks,chunk, uploadFolderPath, fileName, ext);
        int chunksNumber = Integer.parseInt(chunks);

        if (allUploaded) {
            if (mergeFile(chunksNumber, ext, guid, uploadFolderPath)){
                File file = fileService.saveOrUpdate(new File(guid + ext, md5, new Date()));
                System.out.println("file.getId()-->"+file.getId());
            }



        }
    }
}



