package root.demo.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name="work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private ArrayList<String> keyTerms;

    private boolean accepted;

    @ManyToOne
    private ScienceArea scienceArea;

    public Work(){

    }


}
