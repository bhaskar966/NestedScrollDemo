# Nested Scroll Connection for Jetpack Compose

A demonstration of creating unified scrolling experiences in Jetpack Compose applications with nested scrollable components.

## Problem

Nested scrollable components in Jetpack Compose (like a `LazyVerticalGrid` inside a `LazyColumn`) do not automatically propagate scroll events to their parent containers. This creates a disjointed user experience where users must scroll through the nested component completely before the parent container begins to scroll.

```
Vertically scrollable component was measured with an infinity maximum height constraints, which is disallowed. One of the common reasons is nesting layouts like LazyColumn and Column(Modifier.verticalScroll()). If you want to add a header before the list of items please add a header as a separate item() before the main items() inside the LazyColumn scope. There are could be other reasons for this to happen: your ComposeView was added into a LinearLayout with some weight, you applied Modifier.wrapContentSize(unbounded = true) or wrote a custom layout. Please try to remove the source of infinite constraints in the hierarchy above the scrolling container.
```

## Solution

This project demonstrates a simple `NestedScrollConnection` implementation that forwards scroll events from a child scrollable component to its parent. This creates a seamless, unified scrolling experience.

## Features

- Synchronize scrolling between nested scrollable components
- Configurable scroll speed factor
- Compatible with all Compose scrollable components
- Minimal implementation with no external dependencies
- Composable-friendly API

## Usage

### The Scroll Connection Implementation

The implementation is available as a Gist file. You can view and download it here:
[NestedScrollConnection Gist](https://gist.github.com/bhaskar966/b8ae46ace8d8ee4c93f878abd14e1204)

Demo video: [Watch it on Youtube](https://youtu.be/pJBZMp1AyIQ)

### Using the Scroll Connection

```kotlin
@Composable
fun NestedScrollDemo(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    val nestedScrollConnection = rememberForwardingScrollConnection(scrollState)

    LazyColumn(
        state = scrollState,
        modifier = modifier
    ) {
        item {
            HorizontalPager(
                state = rememberPagerState(pageCount = {3}),
            ) { horizontalPagerIndex ->
                HorizontalItem(
                    itemIndex = horizontalPagerIndex,
                    modifier = Modifier.height(200.dp)
                )
            }
        }

        item {
            FixedLazyVerticalGrid(
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
            )
        }
    }
}
```

## How It Works

The `NestedScrollConnection` intercepts scroll events from the child component and forwards them to the parent scrollable component:

1. **Event Interception**: Hooks into `onPreScroll` and `onPostScroll` events
2. **Scroll Forwarding**: Uses coroutines to programmatically scroll the parent component
3. **Configurable Speed**: Adjusts the scroll speed with a configurable factor

This creates the illusion of a single unified scrollable surface, providing a much better user experience.

## Demo Project Structure

The demo project includes:

- `MainActivity.kt`: The entry point with basic setup
- `NestedScrollDemo.kt`: A composable demonstrating the nested scroll connection
- `HorizontalItem.kt` and `VerticalItem.kt`: Simple item composables for demonstration
- `rememberForwardingScrollConnection.kt`: The core utility function

## Use Cases

This technique is particularly useful for:

- Apps with complex layouts that include both horizontal and vertical scrollable components
- Media galleries with grid layouts inside scrollable pages
- E-commerce apps with product categories and product grids
- Any app where you want to create a seamless scrolling experience with nested components

## License

```
MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
