package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.task.Type.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("MULTIPLE")
public class NewMultiplechoice extends Task{
    private Type type =Type.MULTIPLE_CHOICE;
    @ElementCollection
    @CollectionTable(name = "multi_options", joinColumns = @JoinColumn(name = "choice_id"))
    private List<Options> options = new ArrayList<>();
}

















