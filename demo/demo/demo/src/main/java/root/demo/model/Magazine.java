package root.demo.model;

import javax.persistence.*;

@Entity
@Table(name="magazine")
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String issn;

    private boolean openAccess;

    //urednici

    //recenzenti


    public Magazine(){

    }
}
