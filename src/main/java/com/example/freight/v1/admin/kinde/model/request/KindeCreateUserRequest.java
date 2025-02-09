package com.example.freight.v1.admin.kinde.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class KindeCreateUserRequest {
    private UserProfile profile;
    private List<Identity> identities;
}
