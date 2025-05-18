package br.com.alura.AluraFake.task.dto;

@Getter
@Setter
public class NewOpenTextDTO {
    private Long courseId;
    @NotNull(message = "Course ID is required")
    @NotBlank(message = "Statement is required")
    @Size(min = 4, max = 255, message = "Statement must be between 4 and 255 characters")
    private String statement;
    @Positive(message = "Order must be a positive integer")
    private Integer orderr;


    public NewOpenTextDTO(String statement, Integer orderr, Long courseId) {
        this.statement = statement;
        this.orderr = orderr;
        this.courseId = courseId;
    }

    public NewOpenTextDTO() {
    }
}
