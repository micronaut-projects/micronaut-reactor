/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package io.micronaut.reactor.http.client;

import io.micronaut.json.JsonMapper;
import io.micronaut.json.JsonMapperSupplier;
import io.micronaut.serde.ObjectMapper;

/**
 *
 * @author graemerocher
 */
public class TempJsonMapperProvider implements JsonMapperSupplier {

    @Override
    public JsonMapper get() {
        return ObjectMapper.getDefault();
    }

}
