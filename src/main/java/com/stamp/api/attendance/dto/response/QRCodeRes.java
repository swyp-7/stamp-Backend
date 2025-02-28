package com.stamp.api.attendance.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record QRCodeRes(
    List<Byte> byteArr
){
    public static QRCodeRes of (byte[] byteArray) {
        return new QRCodeRes(IntStream.range(0, byteArray.length)
                .mapToObj(i -> byteArray[i])
                .collect(Collectors.toList()));
    }
}
