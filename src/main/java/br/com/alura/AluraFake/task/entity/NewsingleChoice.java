package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.task.Type.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.loadtime.Options;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("SINGLE")
public class NewSingleChoice extends Task {

    private Type type =Type.SINGLE_CHOICE;
    @ElementCollection
    @CollectionTable(name = "single_ptions", joinColumns = @JoinColumn(name = "choice_id"))
    private List<Options> option = new ArrayList<>();


}
