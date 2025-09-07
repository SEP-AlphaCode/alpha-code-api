package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.AccountEnum;
import com.alphacode.alphacodeapi.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Pattern(regexp = "^(\\+84|0)[0-9]{9,10}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 0, message = "Gender invalid (0=Unknown, 1=Male, 2=Female)")
    @Max(value = 2, message = "Gender invalid (0=Unknown, 1=Male, 2=Female)")
    private Integer gender;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastEdited;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String image;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String bannedReason;

    @NotNull(message = "Role ID is required")
    private UUID roleId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roleName;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return AccountEnum.fromCode(this.status);
    }

    @JsonProperty(value = "genderText", access = JsonProperty.Access.READ_ONLY)
    public String getGenderText() {
        return GenderEnum.fromCode(this.gender);
    }
}
