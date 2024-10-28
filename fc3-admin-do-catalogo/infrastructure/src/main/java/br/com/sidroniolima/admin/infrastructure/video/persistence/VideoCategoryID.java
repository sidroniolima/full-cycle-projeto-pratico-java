package br.com.sidroniolima.admin.infrastructure.video.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VideoCategoryID implements Serializable {

    @Column(name = "video_id", nullable = false)
}
