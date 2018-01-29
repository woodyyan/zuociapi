package com.easystudio.api.zuoci.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.data.geo.Point;

import javax.persistence.*;

@Entity
@Table(name = "deleted_phrase")
public class DeletedPhrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "last_modified_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime lastModifiedTime;

    @Column(name = "location")
    private String location;

    @Column(name = "point")
    private Point point;

    @Column(name = "created_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdTime;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
