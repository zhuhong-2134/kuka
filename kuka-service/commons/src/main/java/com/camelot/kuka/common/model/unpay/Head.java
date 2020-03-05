package com.camelot.kuka.common.model.unpay;

import com.camelot.kuka.common.utils.DateUtils;
import lombok.Data;

import java.util.Date;
@Data
public class Head {
    private Long requestTime = Long.parseLong(DateUtils.dateToStr(new Date(), "yyyyMMddHHmmss"));//交易请求时间
}
