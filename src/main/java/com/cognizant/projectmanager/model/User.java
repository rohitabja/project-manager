package com.cognizant.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by hp on 10-08-2019.
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonPropertyOrder({"userId", "firstName", "lastName", "employeeId", "deleted"})
public class User {

    private Integer userId;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotNull
    private Integer employeeId;

    private boolean deleted;

}
