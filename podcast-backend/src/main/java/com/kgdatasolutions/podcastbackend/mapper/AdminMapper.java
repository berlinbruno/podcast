package com.kgdatasolutions.podcastbackend.mapper;

import com.kgdatasolutions.podcastbackend.Model.AppUser;
import com.kgdatasolutions.podcastbackend.dto.admin.AdminResponse;
import com.kgdatasolutions.podcastbackend.service.GcsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AdminMapper {

    private  final GcsService gcsService;
    public AdminResponse mapToAdminResponse(AppUser appUser) {
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setAuthorName(appUser.getName());
        adminResponse.setPodcastId(appUser.getId());
        adminResponse.setPodcastTitle(appUser.getTitle());
        adminResponse.setCategory(appUser.getCategory());
        adminResponse.setLocked(appUser.isLocked());

        // Generate signed URL for profile image if not null
        if (appUser.getProfileUrl() != null) {
            try {
                String signedProfileImageUrl = gcsService.generateSignedUrlForDownload(appUser.getProfileUrl());
                adminResponse.setAuthorProfileUrl(signedProfileImageUrl);
            } catch (IOException e) {
                // Handle the IOException as needed
                // For example, log the error or set a default URL
                e.printStackTrace();
            }
        }
        // Assuming noOfEpisodes is the number of episodes associated with the user
        if (appUser.getEpisodes() != null){
        adminResponse.setNoOfEpisodes(appUser.getEpisodes().size()); // Or whatever logic you use to get the number of episodes

        }
        return adminResponse;
    }
}