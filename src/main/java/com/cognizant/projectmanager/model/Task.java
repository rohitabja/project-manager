package com.cognizant.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonPropertyOrder({"taskId", "taskName", "startDate", "endDate", "priority", "completed",
        "parentTask", "project", "user", "parent"})
public class Task {

    private Integer taskId;
    @NotEmpty
    private String taskName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private Integer priority;
    private boolean completed;
    private ParentTask parentTask;
    @NotNull
    private User user;
    @NotNull
    private Project project;
    private boolean parent;

}
