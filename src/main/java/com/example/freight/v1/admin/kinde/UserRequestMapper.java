package com.example.freight.v1.admin.kinde;

import com.example.freight.auth.models.request.UserRequest;
import com.example.freight.v1.admin.kinde.model.request.KindeCreateUserRequest;
import com.example.freight.v1.admin.kinde.model.request.Identity;
import com.example.freight.v1.admin.kinde.model.request.UserProfile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserRequestMapper {

    private static final String EMAIL_KEY = "email";
    private static final String USERNAME_KEY = "username";

    public static KindeCreateUserRequest map(UserRequest userRequest) {
        UserProfile profile = new UserProfile(userRequest.firstName(), userRequest.lastName());

        Map<String, String> emailDetails = new HashMap<>();
        emailDetails.put(EMAIL_KEY, userRequest.email());

        Map<String, String> usernameDetails = new HashMap<>();
        usernameDetails.put(USERNAME_KEY, userRequest.username());

        Identity emailIdentity = new Identity(EMAIL_KEY, emailDetails);
        Identity usernameIdentity = new Identity(USERNAME_KEY, usernameDetails);
        return new KindeCreateUserRequest(profile, Arrays.asList(emailIdentity, usernameIdentity));
    }
}