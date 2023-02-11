#![cfg_attr(not(test), no_main)]
#![allow(unused)]

mod test;

fn clamp<T>(num: T, from: impl std::ops::RangeBounds<T>, to: impl std::ops::RangeBounds<T>) -> f64
where
    T: TryInto<f64> + Copy,
    <T as TryInto<f64>>::Error: std::fmt::Debug,
{
    use std::ops::Bound::{self, *};

    let num: f64 = num.try_into().unwrap();

    fn unwrap_bound<T, E>(bound: Bound<&T>) -> E
    where
        T: TryInto<E> + Copy,
    {
        let (Included(inner) | Excluded(inner)) = bound else {
            panic!("range is unbound")
        };

        let Ok(inner) = (*inner).try_into() else {
            panic!("cannot try into")
        };

        inner
    }

    let from_start = unwrap_bound(from.start_bound());
    let from_end = unwrap_bound(from.end_bound());

    let to_start = unwrap_bound(to.start_bound());
    let to_end = unwrap_bound(to.end_bound());

    // clamp
    if num <= from_start {
        return to_start;
    }

    if num >= from_end {
        return to_end;
    }

    let abs_range = from_start + from_end;
    let pos = num / abs_range;

    let abs_range_dest = (to_start - to_end).abs();
    let pos_dest = pos * abs_range_dest;

    to_start + pos_dest
}
