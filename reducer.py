import sys

total_temp = 0.0
total_count = 0

for line in sys.stdin:
    parts = line.strip().split('\t')
    if len(parts) == 3:
        _, value, count = parts
        try:
            total_temp += float(value)
            total_count += int(count)
        except:
            continue

if total_count > 0:
    mean = total_temp / total_count
    print(f"Global Mean Temperature:\t{mean:.2f}")
else:
    print("No data")
