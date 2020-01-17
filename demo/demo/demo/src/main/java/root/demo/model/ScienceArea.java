package root.demo.model;

import javax.persistence.*;

@Entity
@Table(name=" sciencearea")
public class ScienceArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private int code;

    @Column(nullable = false)
    private String name;

    public ScienceArea(){

    }

    public ScienceArea(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
