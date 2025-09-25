package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.DeviceDto;
import com.alphacode.alphacodeapi.entity.Device;

public class DeviceMapper {
    public static DeviceDto toDto(Device device) {
        if (device == null) {
            return null;
        }

        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setSpaceId(device.getSpaceId());
        if (device.getSpace() != null) {
            deviceDto.setSpaceName(device.getSpace().getName());
        }
        deviceDto.setDeviceName(device.getDeviceName());
        deviceDto.setTopicSub(device.getTopicSub());
        deviceDto.setTopicPub(device.getTopicPub());
        deviceDto.setMetadata(device.getMetadata());
        deviceDto.setType(device.getType());
        deviceDto.setIpConfig(device.getIpConfig());
        deviceDto.setCreatedDate(device.getCreatedDate());
        deviceDto.setLastUpdate(device.getLastUpdate());
        deviceDto.setStatus(device.getStatus());
        deviceDto.setLastSeen(device.getLastSeen());
        deviceDto.setPowerState(device.getPowerState());
        return deviceDto;
    }

    public static Device toEntity(DeviceDto deviceDto) {
        if (deviceDto == null) {
            return null;
        }

        Device device = new Device();
        device.setId(deviceDto.getId());
        device.setSpaceId(deviceDto.getSpaceId());
        device.setDeviceName(deviceDto.getDeviceName());
        device.setType(deviceDto.getType());
        device.setTopicSub(deviceDto.getTopicSub());
        device.setTopicPub(deviceDto.getTopicPub());
        device.setMetadata(deviceDto.getMetadata());
        device.setIpConfig(deviceDto.getIpConfig());
        device.setCreatedDate(deviceDto.getCreatedDate());
        device.setLastUpdate(deviceDto.getLastUpdate());
        device.setStatus(deviceDto.getStatus());
        device.setLastSeen(deviceDto.getLastSeen());
        device.setPowerState(deviceDto.getPowerState());
        return device;
    }
}
