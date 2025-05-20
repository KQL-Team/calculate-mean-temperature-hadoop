import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MeanTemperatureYearlyMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        // Skip header
        if (key.get() == 0 && line.toLowerCase().contains("time")) {
            return;
        }

        String[] parts = line.split(",");
        if (parts.length < 2) return; // skip malformed lines

        String timeStr = parts[0].trim();  // e.g. "2006-01-01 00:00:00"
        String year = timeStr.split("-")[0];  // Extract "2006"

        for (int i = 1; i < parts.length; i++) {
            try {
                float temp = Float.parseFloat(parts[i].trim());
                context.write(new Text(year), new FloatWritable(temp));
            } catch (NumberFormatException ignored) {
            }
        }
    }
}
