package be.iccbxl.pid.model;

import javax.persistence.*;
import java.util.*;

import com.github.slugify.Slugify;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="locations")
public class Location {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String slug;

    private String designation;
    private String address;

    @ManyToOne
    @JoinColumn(name="locality_id", nullable=false)
    private Locality locality;

    private String website;
    private String phone;

    @OneToMany(targetEntity=Show.class, mappedBy="location")
    private List<Show> shows = new ArrayList<>();

    @OneToMany(targetEntity=Representation.class, mappedBy="location")
    private List<Representation> representations = new ArrayList<>();


    public Location() { }

    public Location(String slug, String designation, String address, Locality locality, String website, String phone) {
        Slugify slg = new Slugify();

        this.slug = slg.slugify(designation);
        this.designation = designation;
        this.address = address;
        this.locality = locality;
        this.website = website;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    private void setSlug(String slug) {
        this.slug = slug;
    }
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;

        Slugify slg = new Slugify();

        this.setSlug(slg.slugify(designation));
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality.removeLocation(this);
        this.locality = locality;
        this.locality.addLocation(this);
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /* @Override
     public String toString() {
         return "Location [id=" + id + ", slug=" + slug + ", designation=" + designation
                 + ", address=" + address	+ ", locality=" + locality + ", website="
                 + website + ", phone=" + phone + "]";
     } */
    public List<Show> getShows() {
        return shows;
    }
    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
    public Location addShow(Show show) {
        if(!this.shows.contains(show)) {
            this.shows.add(show);
            show.setLocation(this);
        }

        return this;
    }
    public Location removeShow(Show show) {
        if(this.shows.contains(show)) {
            this.shows.remove(show);
            if(show.getLocation().equals(this)) {
                show.setLocation(null);
            }
        }

        return this;
    }
    @Override
    public String toString() {
        return "Location [id=" + id + ", slug=" + slug + ", designation=" + designation
                + ", address=" + address	+ ", locality=" + locality + ", website="
                + website + ", phone=" + phone + ", shows=" + shows.size() + "]";
    }

}

