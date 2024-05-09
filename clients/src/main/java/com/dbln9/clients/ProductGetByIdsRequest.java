package com.dbln9.clients;

import java.util.List;

public record ProductGetByIdsRequest(List<Long> ids) {
}
