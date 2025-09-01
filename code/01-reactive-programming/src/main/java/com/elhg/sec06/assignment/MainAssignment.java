package com.elhg.sec06.assignment;

import com.elhg.commons.Util;

public class MainAssignment {
    /**
     * 1. ExternalServiceClient emite datos como : nombre:categoria:precio:cantidad
     * 2. ExternalServiceClient es un hot publisher, emite datos si hay al menos 2 suscriptores
     * 3. InventoryService y RenueveService son subscriptores de ExternalServiceClient
     * 4. InventoryService guarda en su "base de datos" (un Map) la cantidad de productos por categoria,
     *    empieza en 500 y se van restando las cantidades de los pedidos que van llegando
     * 5. RenueveService guarda en su "base de datos" (un Map) el dinero generado por categoria,
     *
     */


    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        var inventoryService = new InventoryService();
        var renueveService = new RenueveService();

        client.getOrderFlux()
                .subscribe(inventoryService::consume);

        client.getOrderFlux()
                .subscribe(renueveService::consume);

        inventoryService.stream()
                .subscribe(Util.getDefaultSubscriber("Inventory DB"));

        renueveService.stream()
                .subscribe(Util.getDefaultSubscriber("Renueve DB"));

        Util.sleepSeconds(30);

    }
}
