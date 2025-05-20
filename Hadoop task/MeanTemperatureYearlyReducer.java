import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MeanTemperatureYearlyReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {

    @Override
    protected void reduce(Text key, Iterable<FloatWritable> values, Context context)
            throws IOException, InterruptedException {

        double sum = 0.0;
        int count = 0;

        for (FloatWritable val : values) {
            sum += val.get();
            count++;
        }

        float mean = (float)(sum / count);

        context.write(new Text(key.toString() + "_MeanTemperature"), new FloatWritable(mean));
    }
}
