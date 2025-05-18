package br.com.alura.AluraFake.task.dto;

@Getter
@Setter
public class NewSingleChoiceDTO {
    @NotNull(message = "Course ID is required")
    private Long courseId;
    @NotBlank(message = "Statement is required")
    @Size(min = 4, max = 255, message = "Statement must be between 4 and 255 characters")
    private String statement;
    @Positive(message = "Order must be a positive integer")
    private Integer orderr;

    @ElementCollection
    @CollectionTable(name = "single_choice_options", joinColumns = @JoinColumn(name = "Single_id"))
    private List<Options> option = new ArrayList<>();

    public NewSingleChoiceDTO(Long courseId, String statement, Integer orderr, List<Options> option) {
        this.courseId = courseId;
        this.statement = statement;
        this.orderr = orderr;
        this.option = option;
    }

    public NewSingleChoiceDTO() {

    }

}