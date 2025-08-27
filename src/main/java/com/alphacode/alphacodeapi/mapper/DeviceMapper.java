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
        deviceDto.setType(device.getType());
        deviceDto.setIpConfig(device.getIpConfig());
        deviceDto.setCreatedDate(device.getCreatedDate());
        deviceDto.setLastUpdate(device.getLastUpdate());
        deviceDto.setStatus(device.getStatus());
        return deviceDto;
    }

    public static Device toEntity(DeviceDto deviceDto) {
        if (deviceDto == null) {
            return null;
        }

        Device device = new Device();
        device.setId(deviceDto.getId());
        device.setSpaceId(deviceDto.getSpaceId());
        device.setType(deviceDto.getType());
        device.setIpConfig(deviceDto.getIpConfig());
        device.setCreatedDate(deviceDto.getCreatedDate());
        device.setLastUpdate(deviceDto.getLastUpdate());
        device.setStatus(deviceDto.getStatus());
        return device;
    }
}
