package com.camelot.kuka.common.utils;

import com.fehorizon.commonService.model.common.Result;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * <p>Description: [图片工具类]</p>
 * Created on 2019/9/27
 * @author <a href="mailto: hexiaobo@camelotchina.com">贺小波</a>
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class ImgUtil {

    /**进阶大小*/
    private static final Long ADVANCED = 1024L;

    /**
     * <p>Description:[图片检验]</p>
     * Created on 2019/9/27
     * @param fileUrl 图片路径
     * @param sizeMax 图片大小最大值-KB为单位
     * @param heightMax 图片高度最大值-PX为单位
     * @param widthMax 图片宽度最大值-PX为单位
     * @return com.fehorizon.commonService.model.common.Result<java.lang.String>
     * @author 贺小波
     */
    public static Result<String> imgSpecsCheck(String fileUrl, int sizeMax, int heightMax, int widthMax) {
        Result<String> result = imgSpecsCheck(fileUrl, null, sizeMax, null, heightMax, null, widthMax);
        return result;
    }

    /**
     * <p>Description:[图片检验]</p>
     * Created on 2019/9/27
     * @param fileUrl 图片路径
     * @param sizeMin 图片大小最小值-KB为单位
     * @param sizeMax 图片大小最大值-KB为单位
     * @param heightMin 图片高度最小值-PX为单位
     * @param heightMax 图片高度最大值-PX为单位
     * @param widthMin 图片宽度最小值-PX为单位
     * @param widthMax 图片宽度最大值-PX为单位
     * @return com.fehorizon.commonService.model.common.Result<java.lang.String>
     * @author 贺小波
     */
    public static Result<String> imgSpecsCheck(String fileUrl, Integer sizeMin, Integer sizeMax, Integer heightMin, Integer heightMax, Integer widthMin, Integer widthMax) {
        if (StringUtils.isBlank(fileUrl)) {
            return Result.error("图片地址为空");
        }
        try {
            File file = new File(fileUrl);
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage == null) {
                return Result.error("文件非图片");
            }
            Long fileSize = file.length() / ADVANCED;
            int size = fileSize.intValue();
            if (sizeMin != null && sizeMin > size) {
                return Result.error("图片大小" + size + "小于限制最小值" + sizeMin);
            }
            if (sizeMax != null && sizeMax < size) {
                return Result.error("图片大小" + size + "大于限制最大值" + sizeMax);
            }
            int height = bufferedImage.getHeight();
            if (heightMin != null && heightMin > height) {
                return Result.error("图片高度" + height + "小于限制最小值" + heightMin);
            }
            if (heightMax != null && heightMax < height) {
                return Result.error("图片高度" + height + "大于限制最大值" + heightMax);
            }
            int width = bufferedImage.getWidth();
            if (widthMin != null && widthMin > width) {
                return Result.error("图片宽度" + width + "小于限制最小值" + widthMin);
            }
            if (widthMax != null && widthMax < width) {
                return Result.error("图片宽度" + width + "大于限制最大值" + widthMax);
            }
        } catch (IOException e) {
            return Result.error("图片校验出现异常:" + ExceptionUtil.getExceptionToString(e));
        }
        return Result.success();
    }

    /**
     * <p>Description:[图片检验]</p>
     * Created on 2019/9/27
     * @param base64str 图片base64码
     * @param heightMin 图片高度最小值-PX为单位
     * @param heightMax 图片高度最大值-PX为单位
     * @param widthMin 图片宽度最小值-PX为单位
     * @param widthMax 图片宽度最大值-PX为单位
     * @return com.fehorizon.commonService.model.common.Result<java.lang.String>
     * @author 贺小波
     */
    public static Result<String> imgSpecsBase64Check(String base64str, Integer heightMin, Integer heightMax, Integer widthMin, Integer widthMax) {
        if (StringUtils.isBlank(base64str)) {
            return Result.error("base64码为空");
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(EncodeUtil.getBase64Byte(base64str)));
            int height = bufferedImage.getHeight();
            if (heightMin != null && heightMin > height) {
                return Result.error("图片高度" + height + "小于限制最小值" + heightMin);
            }
            if (heightMax != null && heightMax < height) {
                return Result.error("图片高度" + height + "大于限制最大值" + heightMax);
            }
            int width = bufferedImage.getWidth();
            if (widthMin != null && widthMin > width) {
                return Result.error("图片宽度" + width + "小于限制最小值" + widthMin);
            }
            if (widthMax != null && widthMax < width) {
                return Result.error("图片宽度" + width + "大于限制最大值" + widthMax);
            }
        } catch (IOException e) {
            return Result.error("图片校验出现异常:" + ExceptionUtil.getExceptionToString(e));
        }
        return Result.success();
    }

}
