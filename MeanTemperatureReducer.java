import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MeanTemperatureReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {

    @Override
    protected void reduce(Text key, Iterable<FloatWritable> values, Context context)
            throws IOException, InterruptedException {

        double sum = 0.0;
        int count = 0;

        for (FloatWritable val : values) {
            sum += val.get();
            count += 1;
        }

        float mean = (float)(sum / count);


        context.write(new Text(key.toString() + "_TotalSum"), new FloatWritable((float) sum));
        context.write(new Text(key.toString() + "_TotalCount"), new FloatWritable(count));
        context.write(new Text(key.toString() + "_Mean"), new FloatWritable(mean));
    }
}
