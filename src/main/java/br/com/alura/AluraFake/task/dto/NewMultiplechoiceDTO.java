package br.com.alura.AluraFake.task.dto;

@Getter
@Setter
public class NewMultiplechoiceDTO {
    @NotNull(message = "Course ID is required")
    private Long courseId;
    @NotBlank(message = "Statement is required")
    @Size(min = 4, max = 255, message = "Statement must be between 4 and 255 characters")
    private String statement;
    @Positive(message = "Order must be a positive integer")
    private Integer orderr;
    @ElementCollection
    @CollectionTable(name = "multiple_choice_options", joinColumns = @JoinColumn(name = "multiple_id"))
    private List<Options> option = new ArrayList<>();


    public NewMultiplechoiceDTO(Long courseId, String statement, Integer orderr, Course course) {
        this.courseId = courseId;
        this.statement = statement;
        this.orderr = orderr;

    }

    public NewMultiplechoiceDTO() {
    }
}


