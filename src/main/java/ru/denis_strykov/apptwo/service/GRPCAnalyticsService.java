package ru.denis_strykov.apptwo.service;

import ru.denis_strykov.grpccommon.MeasurementType;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import ru.denis_strykov.apptwo.model.Data;
import ru.denis_strykov.grpccommon.AnalyticsServerGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.denis_strykov.grpccommon.GRPCAnalyticsRequest;
import ru.denis_strykov.grpccommon.GRPCData;

import java.time.ZoneOffset;
import java.util.List;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class GRPCAnalyticsService extends AnalyticsServerGrpc.AnalyticsServerImplBase {

    private final DataService dataService;

    @Override
    public void askForData(GRPCAnalyticsRequest request, StreamObserver<GRPCData> responseObserver) {
        List<Data> data = dataService.getWithBatch(request.getBatchSize());
        for (Data d : data) {
            GRPCData dataRequest = GRPCData.newBuilder()
                    .setSensorId(d.getSensorId())
                    .setTimestamp(
                            Timestamp.newBuilder()
                                    .setSeconds(
                                            d.getTimestamp()
                                                    .toEpochSecond(ZoneOffset.UTC)
                                    )
                                    .build())
                    .setMeasurementType(
                            MeasurementType.valueOf(d.getMeasurementType().name())
                    )
                    .setMeasurement(d.getMeasurement())
                    .build();
            responseObserver.onNext(dataRequest);
        }
        log.info("Batch was sent.");
        responseObserver.onCompleted();
    }
}
