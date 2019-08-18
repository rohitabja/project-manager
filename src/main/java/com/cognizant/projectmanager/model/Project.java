package com.cognizant.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hp on 10-08-2019.
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonPropertyOrder({"projectId", "projectName", "startDate", "endDate", "priority", "manager", "tasks", "updatedDate"})
public class Project {

    private Integer projectId;
    @NotEmpty
    private String projectName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private Integer priority;
    @NotNull
    private User manager;
    private List<Task> tasks;
    private LocalDateTime updatedDate;
    private Integer noOfTasksCompleted;

}
