package br.com.alura.AluraFake.task.entity;
import br.com.alura.AluraFake.task.Type.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class NewMultiplechoice extends Task {

    @Transient
    protected Type type = Type.MULTIPLE_CHOICE;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Options> options = new ArrayList<>();
}