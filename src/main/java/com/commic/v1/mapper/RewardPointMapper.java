package com.commic.v1.mapper;

import com.commic.v1.dto.responses.AttendanceResponse;
import com.commic.v1.entities.RewardPoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RewardPointMapper {
    @Mapping(source = "point", target = "point")
    @Mapping(source = "date", target = "date")
    AttendanceResponse toAttendanceResponse(RewardPoint rewardPoint);
}
