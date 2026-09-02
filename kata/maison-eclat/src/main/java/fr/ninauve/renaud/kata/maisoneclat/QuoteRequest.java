package fr.ninauve.renaud.kata.maisoneclat;

import fr.ninauve.renaud.kata.maisoneclat.additionalservices.AdditionalServiceType;
import fr.ninauve.renaud.kata.maisoneclat.privileges.ClientTier;

import java.math.BigDecimal;
import java.util.Set;

public record QuoteRequest(String clientName,
                           ClientTier clientTier,
                           LuxuryProduct luxuryProduct,
                           String productName,
                           BigDecimal basePrice,
                           Set<AdditionalServiceType> additionalServiceTypes,
                           DestinationCountry destinationCountry) {}