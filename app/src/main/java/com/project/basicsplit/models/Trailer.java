package com.project.basicsplit.models;

import com.google.common.base.Optional;

public class Trailer {
    private String key;
    private String type;
    private String site;

    public Optional<String> getKey() {
        return key != null ? Optional.of(key) : Optional.<String>absent();
    }

    public Optional<String> getType() {
        return type != null ? Optional.of(type) : Optional.<String>absent();
    }

    public Optional<String> getSite() {
        return site != null ? Optional.of(site) : Optional.<String>absent();
    }
}
