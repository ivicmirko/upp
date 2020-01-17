package root.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="magazine")
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String issn;

    @Column
    private boolean openAccess; //ako je open access naplata se vrsi autorima
    
    @Column
    private String name;

    @Column
    private boolean active;
    
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<ScienceArea> scienceAreas=new ArrayList<>();
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="main_editor_id", nullable=false)
    private SystemUser mainEditor;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<SystemUser> eidtors=new ArrayList<>();
    
    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<SystemUser> reviewers=new ArrayList<>();
    //urednici

    //recenzenti


    public Magazine(){

    }

	public Magazine(long id, String issn, boolean openAccess, String name, boolean active,
			List<ScienceArea> scienceAreas, SystemUser mainEditor, List<SystemUser> eidtors,
			List<SystemUser> reviewers) {
		super();
		this.id = id;
		this.issn = issn;
		this.openAccess = openAccess;
		this.name = name;
		this.active = active;
		this.scienceAreas = scienceAreas;
		this.mainEditor = mainEditor;
		this.eidtors = eidtors;
		this.reviewers = reviewers;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<ScienceArea> getScienceAreas() {
		return scienceAreas;
	}

	public void setScienceAreas(List<ScienceArea> scienceAreas) {
		this.scienceAreas = scienceAreas;
	}

	public SystemUser getMainEditor() {
		return mainEditor;
	}

	public void setMainEditor(SystemUser mainEditor) {
		this.mainEditor = mainEditor;
	}

	public List<SystemUser> getEidtors() {
		return eidtors;
	}

	public void setEidtors(List<SystemUser> eidtors) {
		this.eidtors = eidtors;
	}

	public List<SystemUser> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<SystemUser> reviewers) {
		this.reviewers = reviewers;
	}
    
    
}
