package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.OrganizationDto;
import com.alphacode.alphacodeapi.entity.Organization;

public class OrganizationMapper {
    public static OrganizationDto toDto(Organization organization) {
        if (organization == null) {
            return null;
        }
        OrganizationDto dto = new OrganizationDto();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setLocation(organization.getLocation());
        dto.setCreateDate(organization.getCreateDate());
        dto.setLastUpdate(organization.getLastUpdate());
        dto.setStatus(organization.getStatus());
        dto.setEmail(organization.getEmail());
        dto.setPhone(organization.getPhone());
        return dto;
    }

    public static Organization toEntity(OrganizationDto dto) {
        if (dto == null) {
            return null;
        }
        Organization organization = new Organization();
        organization.setId(dto.getId());
        organization.setName(dto.getName());
        organization.setLocation(dto.getLocation());
        organization.setCreateDate(dto.getCreateDate());
        organization.setLastUpdate(dto.getLastUpdate());
        organization.setStatus(dto.getStatus());
        organization.setEmail(dto.getEmail());
        organization.setPhone(dto.getPhone());
        return organization;
    }

    public static void patchUpdate(Organization organization, OrganizationDto dto) {
        if (dto.getName() != null) {
            organization.setName(dto.getName());
        }
        if (dto.getLocation() != null) {
            organization.setLocation(dto.getLocation());
        }
        if (dto.getStatus() != null) {
            organization.setStatus(dto.getStatus());
        }
        if (dto.getEmail() != null) {
            organization.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            organization.setPhone(dto.getPhone());
        }
        if (dto.getLastUpdate() != null) {
            organization.setLastUpdate(dto.getLastUpdate());
        }
    }
}
